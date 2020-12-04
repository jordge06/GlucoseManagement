import java.util.ArrayList;

public class HelloWorld{

     public static void main(String []args){
        
        ArrayList<String> data = new ArrayList<>();
        for (int i = 1; i < 41; i++) {
            //data.add(i + "");
            if (i % 2 == 0) {
                data.add((i / 2) + "");
            } else {
                double d = i * 0.5;
                data.add(d + "");
            }
        }
        
        data.add(0, "-");
        
        System.out.println(data);
     }
}