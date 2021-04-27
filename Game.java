import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Game{
    //turn, health, and gameWon values
    int turn;
    int p1hp;
    int p2hp;
    int gameWon;

    //Instantiate Creatures
    Creature empty = new Creature(0," ",0);
    Creature beast = new Creature(-3,"b",4);
    Creature dog = new Creature(-2,"d",3);
    Creature bug = new Creature(0,"'",1);
    Creature[] creatureList = {beast,dog,bug};

    //Instantiate Spells
    Spell wipe = new Spell(-2,"w");
    Spell heal = new Spell(+1,"h");
    Spell none = new Spell(0," ");
    Spell[] spellList = {wipe,heal};

    //Insantiate Hands
    Hand p1hand = new Hand(0); //p1 starts with deck id 0 (defense deck)
    Hand p2hand = new Hand(1); //p2 starts with deck id 1 (offense deck)
    
    //constructor
    public Game(){
        turn = 0;
        p1hp = 13;
        p2hp = 13;
        gameWon = 0;
    }

    //board array
    Creature[] p1board = {empty, empty, empty};
    Creature[] p2board = {empty, empty, empty};

    //runs on start
    public void init(){
        p1hand.handInit();
        p2hand.handInit();
        for (int k=0; k<4; k++){
            p1hand.hdraw();
            p2hand.hdraw();
        }
    }

    //converts creature id to creature
    public Creature creatureConvert(String n){
        int i = 0;
        for (i=0;i<creatureList.length;i++){
            if (n.equals(creatureList[i].getName())){
                return creatureList[i];
            }
        }
        return empty;
    }

    //converts spell id to spell
    public Spell spellConvert(String n){
        int i = 0;
        for (i=0;i<spellList.length;i++){
            if (n.equals(spellList[i].getName())){
                return spellList[i];
            }
        }
        return none;
    }

    public boolean isCreature(String n){
        int i = 0;
        for (i=0;i<creatureList.length;i++){
            if (n.equals(creatureList[i].getName())){
                return true;
            }
        }
        return false;
    }

    public boolean isSpell(String n){
        int i = 0;
        for (i=0;i<spellList.length;i++){
            if (n.equals(spellList[i].getName())){
                return true;
            }
        }
        return false;
    }


    //displays the board
    public void display(){
        //clear screen
        System.out.print("\033[H\033[2J");  
        System.out.flush();  

        //display
        System.out.println("[" + turn + "]  Player: "+(turn%2+1));
        System.out.println(p1board[0].getName()+"|"+p1board[1].getName()+"|"+p1board[2].getName());
        System.out.println(p1board[0].getPower()+"|"+p1board[1].getPower()+"|"+p1board[2].getPower());
        System.out.println("------");
        System.out.println(p2board[0].getName()+"|"+p2board[1].getName()+"|"+p2board[2].getName());
        System.out.println(p2board[0].getPower()+"|"+p2board[1].getPower()+"|"+p2board[2].getPower());
        System.out.println("P1 Health: " + p1hp + "  P2 Health: " + p2hp);
        if (turn%2 == 0){
            System.out.println("Hand: " + p1hand.getHand());
        }
        else if (turn%2 == 1){
            System.out.println("Hand: " + p2hand.getHand());
        }
    }

    //runs during either player's turn
    public void turn(){
        Scanner in = new Scanner(System.in);
        display();
        

        while (gameWon == 0){
            System.out.println("Enter piece or type 'end': ");
            String piece = in.nextLine();

            //command to end turn
            if (piece.equals("end")){
                //draw a card
                if (turn%2 == 0 && turn != 0){
                    p1hand.hdraw();
                }
                else if (turn%2 == 1){
                    p2hand.hdraw();
                }
                turn = turn + 1;

                System.out.println("Player " + (turn%2+1) + "'s turn");
                display();
            }

            //command to exit
            else if (piece.equals("exit")){
                gameWon = 1;
            }

            //run place function
            else{
                place(piece);
            }
        }
    }

    //places card on the board
    public void place(String keyin){
        //get creature id from user
        Scanner in = new Scanner(System.in);
        if (turn%2 == 0){
            if (isCreature(keyin)){
                if (keyin.equals(p1hand.play(keyin))){
                    //get column # from user
                    System.out.println("Enter Column: ");
                    int column = in.nextInt();
                    if (column>0 && column<4){
                        column = column - 1;
                        //place creature
                        p1board[column] = creatureConvert(keyin);
                        p1hp = p1hp + p1board[column].getCost();
                        //fight after placement
                        fight(column);
                        //display aftermath
                        display();
                    }
                }
            }
            else if (isSpell(keyin)){
                if (keyin.equals(p1hand.play(keyin))){
                    cast(keyin);
                    p1hp = p1hp + spellConvert(keyin).getCost();
                    //display aftermath
                }
            }
        }
        else if (turn%2 == 1){
            if (isCreature(keyin)){
                if (keyin.equals(p2hand.play(keyin))){
                    //get column # from user
                    System.out.println("Enter Column: ");
                    int column = in.nextInt();
                    if (column>0 && column<4){
                        column = column - 1;
                        //place creature
                        p2board[column] = creatureConvert(keyin);
                        p2hp = p2hp + p2board[column].getCost();
                        //fight after placement
                        fight(column);
                        //display aftermath
                        display();
                    }
                }
            }
            
            else if (isSpell(keyin)){
                if (keyin.equals(p2hand.play(keyin))){
                    cast(keyin);
                    p2hp = p2hp + spellConvert(keyin).getCost();
                    //display aftermath
                }
            }
        }
        checkWin();
    }

    //makes cards fight
    public void fight(int column){
        //p1 attacking p2
        if (p1board[column].getPower() > p2board[column].getPower()){
            if (p2board[column] == empty){
                p2hp = p2hp - p1board[column].getPower();
            }
            else{
                p2board[column] = empty;
            }
        }
        //p2 attacking p1
        else if (p2board[column].getPower() > p1board[column].getPower()){
            if (p1board[column] == empty){
                p1hp = p1hp - p2board[column].getPower();
            }
            else{
                p1board[column] = empty;
            }
        }
        checkWin();
    }

    //checks if a player has won
    public void checkWin(){
        if (p1hp < 1){
            System.out.println("P2 Wins!");
            gameWon = 1;
        }
        if (p2hp < 1){
            System.out.println("P1 Wins!");
            gameWon = 1;
        }
    }

    //casts a spell given id (this is the simplest way I could find to do this)
    public void cast(String id){
        //wipe spell
        if (id.equals("w")){
            System.out.println("Enter column to destroy: ");
            Scanner in = new Scanner(System.in);
            int column = in.nextInt();
            if (column>0 && column<4){
                column = column - 1;
                p1board[column] = empty;
                p2board[column] = empty;
            }
        }
        display();
    }

    //main
    public static void main(String[] args){
        Game a = new Game();
        a.init();
        a.turn();        
    }
}
