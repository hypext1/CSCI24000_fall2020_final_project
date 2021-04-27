public class Creature extends Card{
    int power;
    Creature(int c, String n, int p){
        super(c,n);
        this.power = p;
    }

    public int getPower(){
        return power;
    }
}
