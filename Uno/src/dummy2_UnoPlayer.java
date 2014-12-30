import java.util.List;


public class dummy2_UnoPlayer implements UnoPlayer {
	
	UnoPlayer.Color blue = UnoPlayer.Color.BLUE;
	UnoPlayer.Color red = UnoPlayer.Color.RED;
	UnoPlayer.Color yellow = UnoPlayer.Color.YELLOW;
	UnoPlayer.Color green = UnoPlayer.Color.GREEN;
	UnoPlayer.Color wildColor = UnoPlayer.Color.NONE;
	
	UnoPlayer.Rank skip = UnoPlayer.Rank.SKIP;
	UnoPlayer.Rank reverse = UnoPlayer.Rank.REVERSE;
	UnoPlayer.Rank drawTwo = UnoPlayer.Rank.DRAW_TWO;
	UnoPlayer.Rank wildDrawFour = UnoPlayer.Rank.WILD_D4;
	UnoPlayer.Rank wild = UnoPlayer.Rank.WILD;
	
	
	public int play(List<Card> hand, Card upCard, Color calledColor, GameState state){
		
		UnoPlayer.Color colorOfUpCard = upCard.getColor();//Used readable variables to refer to the upCard
		UnoPlayer.Rank rankOfUpCard = upCard.getRank();
		int numOfUpCard = upCard.getNumber();
		
		
		int[] numCardsInHandsOfUpcomingPlayers = state.getNumCardsInHandsOfUpcomingPlayers();//I put these returned arrays into variables to make it easier writing code
		Color[] mostRecentColorCalledByUpcomingPlayers = state.getMostRecentColorCalledByUpcomingPlayers();
		List<Card> playedCards = state.getPlayedCards();
		int[] totalScoreOfUpcomingPlayers = state.getTotalScoreOfUpcomingPlayers();
		
		
		int numBlue = 0;
		int numYellow = 0;
		int numRed = 0;
		int numGreen = 0;
		
		int playedActionCards = 0;
		int playedRegularCards = 0;
		
		UnoPlayer.Color lowestCardColor = null;
		UnoPlayer.Color highestCardColor = null;
		

		
		for (int i = 0; i < hand.size(); i++){//this counts how many of each color I have in my current hand
			
			if (hand.get(i).getColor() == blue){
				
				numBlue++;
			}
			if (hand.get(i).getColor() == yellow){
				
				numYellow++;
			}
            if (hand.get(i).getColor() == red){
				
				numRed++;
			}
            if (hand.get(i).getColor() == green){
	
            	numGreen++;
            }
    
            
		}
		
		for (int i = 0; i < playedCards.size(); i++){
			
			
			if (playedCards.get(i).getRank() != Rank.NUMBER){//this keeps track of how many actions cards were played so far, the more action cards there are...
															 //the more likely I will play a number card so I can save my action cards for the end
				playedActionCards++;
			}
			else{
				playedRegularCards++;
			}
			
			
			
			
		}
		
		

		
		if (!(numBlue > numYellow && numBlue > numRed && numBlue > numGreen)){//these statements determine what color I have the least of
			lowestCardColor = blue;
		}
		else if(!(numYellow > numBlue && numYellow > numRed && numYellow > numGreen)){
			lowestCardColor = yellow;

		}
		else if(!(numRed > numYellow && numRed > numBlue && numRed > numGreen)){
			lowestCardColor = red;
		}
		else {
			lowestCardColor = green;

			}
		if (numBlue > numYellow && numBlue > numRed && numBlue > numGreen){//these statements determine what color I have the most of
			highestCardColor = blue;
		}
		else if(numYellow > numBlue && numYellow > numRed && numYellow > numGreen){
			highestCardColor = yellow;

		}
		else if(numRed > numYellow && numRed > numBlue && numRed > numGreen){
			highestCardColor = red;
		}
		else {
			highestCardColor = green;

			}
		
		
		int playerWithHighestScore = 0;
		int highestScore = totalScoreOfUpcomingPlayers[0];
		for (int score = 0; score < totalScoreOfUpcomingPlayers.length; score++){//first, find out who has the highest score in the overall Uno match so we can use our
			                                      //best cards against this player if possible such as Wild cards and action cards
			
																				 //I want to keep myself in a winning position at all times so trying to disrupt the current winner or player with the second highest points will further
																				 //increase my chances of winning the overall game.
			
			if (totalScoreOfUpcomingPlayers[score] > highestScore){
				playerWithHighestScore = score;
				
				
			}
			
		}
		
		UnoPlayer.Color recentColor = mostRecentColorCalledByUpcomingPlayers[playerWithHighestScore];//whatever color the current winner (if not me) has called, it implies that he or she may still have
																									//more of that color meaning if the player calls Red..it might mean that the player has 4 red cards left.
																									//With this knowledge, we can utilize it and play either a wildcard that helps me out the most or play a number 
																									//of a different color or try and change the color.

		for (int i = 0; i < hand.size(); i++){

			if (hand.get(i).getRank() == wild || hand.get(i).getRank() == wildDrawFour){//play my wild cards first since they are worth the most points
				return i;
			}
		
			if (highestCardColor == calledColor){
				if (hand.get(i).getColor() == highestCardColor){//checks to see which card is the highestCardColor
					return i;//if a wild card was called, we have the advantage so we want to call the color that we have the most of
							   //it returns the index of a card that it first sees which is the same color
					}	
				}
			if(hand.get(i).getRank() == skip || hand.get(i).getRank() == reverse || hand.get(i).getRank() == drawTwo){//if I have these special cards...
				if (hand.get(i).getRank() == rankOfUpCard || hand.get(i).getColor() == colorOfUpCard){//and these special cards are legal to play if the rank or color are same
					return i;
				}
			}
		
			 if(hand.get(i).getColor() == calledColor){//If I have a card as the same color of the called color, play it
					return i;
				}
		
			if (lowestCardColor == colorOfUpCard){//gets rid of my lowest Color card(s) so I can get rid of a certain color in my hand and focus on another
				if (hand.get(i).getColor() == lowestCardColor){
					return i;
				}
			}
		
			
		
			if(numOfUpCard == hand.get(i).getNumber() && numOfUpCard != -1){// if ANY of my cards has the same number, play it!
				return i;
			}
		
		
		
			if(hand.get(i).getColor() == colorOfUpCard){
				return i;//if a regular colored card is the upcard, look for a card in my hand that matches the color
			}
		
		}
		
				
		
		return -1;//if none of my cards work, return -1 or "draw";
		
		
		
		
		
	}
	
	public Color callColor(List<Card> hand){
		
		
		int numBlue = 0;
		int numYellow = 0;
		int numRed = 0;
		int numGreen = 0;
		
		for (int i = 0; i < hand.size(); i++){//first, I am counting the color I have most of in my hand because say I have 7 yellow cards, 
											 //then it only makes sense to call the Color Yellow
			
			if (hand.get(i).getColor() == blue){
				
				numBlue++;
			}
			if (hand.get(i).getColor() == yellow){
				
				numYellow++;
			}
            if (hand.get(i).getColor() == red){
				
				numRed++;
			}
            if (hand.get(i).getColor() == green){
	
            	numGreen++;
            }
            
		}
		
		
		/*After counting all of my colors, now I can just compare them to each other until I see which one I have the most of.
		 * Once one of these statements becomes true(blue is the most abundant) then I will return blue and hopefully the color does not change
		 * for a while so I have a higher chance of getting rid of my cards
		 */
		if (numBlue > numYellow && numBlue > numRed && numBlue > numGreen){
			return blue;
		}
		else if(numYellow > numBlue && numYellow > numRed && numYellow > numGreen){
			return yellow;
		}
		else if(numRed > numYellow && numRed > numBlue && numRed > numGreen){
			return red;
		}
		else {
			return green;
			}
		
		
		
	}

	
}




    /**
     * play - This method is called when it's your turn and you need to
     * choose what card to play.
     *
     * The hand parameter tells you what's in your hand. You can call
     * getColor(), getRank(), and getNumber() on each of the cards it
     * contains to see what it is. The color will be the color of the card,
     * or "Color.NONE" if the card is a wild card. The rank will be
     * "Rank.NUMBER" for all numbered cards, and another value (e.g.,
     * "Rank.SKIP," "Rank.REVERSE," etc.) for special cards. The value of
     * a card's "number" only has meaning if it is a number card. 
     * (Otherwise, it will be -1.)
     *
     * The upCard parameter works the same way, and tells you what the 
     * up card (in the middle of the table) is.
     *
     * The calledColor parameter only has meaning if the up card is a wild,
     * and tells you what color the player who played that wild card called.
     *
     * Finally, the state parameter is a GameState object on which you can 
     * invoke methods if you choose to access certain detailed information
     * about the game (like who is currently ahead, what colors each player
     * has recently called, etc.)
     *
     * You must return a value from this method indicating which card you
     * wish to play. If you return a number 0 or greater, that means you
     * want to play the card at that index. If you return -1, that means
     * that you cannot play any of your cards (none of them are legal plays)
     * in which case you will be forced to draw a card (this will happen
     * automatically for you.)
     */
   

    /**
     * callColor - This method will be called when you have just played a
     * wild card, and is your way of specifying which color you want to 
     * change it to.
     *
     * You must return a valid Color value from this method. You must not
     * return the value Color.NONE under any circumstances.
  **/