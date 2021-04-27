import java.util.*;
  
public class Hand {
    //Insantiate Linked List
    LinkedList<String> hand = new LinkedList<String>();

    int deckid;
    Hand(int deckid){
        this.deckid = deckid;
    }

    //Instantiate Decks
    Deck ddeck = new Deck("b","d","h");
    Deck odeck = new Deck("'","d","w");

    public void handInit(){
        ddeck.deckInit();
        odeck.deckInit();
    }

    //Draw a card from deck to hand
    public void hdraw(){
        //Draw from deck 0 (defense deck)
        if (deckid == 0){
            hand.add(ddeck.draw());
        }
        //Draw from deck 1 (offense deck)
        else if (deckid == 1){
            hand.add(odeck.draw());
        }
    }

    //Returns entire deck
    public LinkedList<String> getHand(){
        return hand;
    }

    //Returns and removes a card from hand
    public String play(String cardn){
        for (int i = 0; i < hand.size(); i++){
            if (hand.get(i).equals(cardn)){
                hand.remove(i);
                return cardn;
            }
        }
        return "err";
    }
}
