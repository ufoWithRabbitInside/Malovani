
import java.util.ArrayList;
import java.util.List;

public class Fill extends Thing{
    public List<Point> actual_border;
    public List<Point> new_border;
    public int back_color;
    public double rnd_to_fill;

    public Fill(int back_color, int color, double rnd_to_fill){
        actual_border = new ArrayList<>();
        new_border = new ArrayList<>();
        this.back_color = back_color;
        this.color = color;
        this.rnd_to_fill = rnd_to_fill;
    }

}
