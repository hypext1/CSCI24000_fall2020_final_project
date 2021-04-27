import java.util.LinkedList;
import java.lang.Math;
  
public class Deck {
    //Instantiate Linked List
    LinkedList<String> deck = new LinkedList<String>();

    String c1;
    String c2;
    String c3;
    Deck(String c1, String c2, String c3){
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
    }
    public void deckInit(){
        int i = 0;
        int j = 0;
        for (i = 0; i < 3; i++){
            for (j = 0; j < 4; j++){
                if (i == 0){
                    deck.add(c1);
                }
                else if (i == 1){
                    deck.add(c2);
                }
                else if (i == 2){
                    deck.add(c3);
                }
            }
        }
    }


    public String draw(){
        int randomNum = (int)(Math.random() * deck.size());
        String outp = deck.get(randomNum);
        deck.remove(randomNum);
        return outp;
    }
}
