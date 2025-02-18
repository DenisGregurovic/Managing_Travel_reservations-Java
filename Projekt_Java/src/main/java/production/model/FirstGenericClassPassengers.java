package production.model;
import java.util.Set;

public class FirstGenericClassPassengers<T> {
    private Set<T> list;

    public FirstGenericClassPassengers(Set<T> list) {
        this.list = list;
    }

    public void outputList() {
        for (T element : list) {
            System.out.println(element);
        }
    }
}
