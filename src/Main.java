
public class Main {



    public static void main(String[] args)  {
        File_processor fp = new File_processor();
        try {
            fp.process();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}