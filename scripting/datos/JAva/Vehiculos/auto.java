import animals.jirafa;
import animals.pato;
import animals.jirafa;

public class auto {
    public void drive() {
        System.out.println("The car is driving.");
    }

    public static void main(String[] args) {
        auto myCar = new auto();
        myCar.drive();

        jirafa myGiraffe = new jirafa();
        myGiraffe.eatLeaves();
    }
}