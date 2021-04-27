abstract class Card{
    int cost;
    String name;
    Card(int c, String n){
        this.cost = c;
        this.name = n;
    }

    public int getCost(){
        return cost;
    }
    public String getName(){
        return name;
    }
}
