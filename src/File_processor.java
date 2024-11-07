import File_read_exceptions.Is_not_file_exception;
import File_read_exceptions.Is_not_readable_file_exception;
import File_read_exceptions.Is_not_writable_file_exception;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;


public class File_processor {
    private HashMap<Character,Integer> symbol_container;

    /**
     * Initializes symbol_container with symbols of english alphabets and zeroes
     */
    private void init_map(){
        this.symbol_container=new HashMap<>();
        for (char symbol = 'a'; symbol <= 'z'; symbol++) {
            this.symbol_container.put(symbol,0);
            this.symbol_container.put(Character.toUpperCase(symbol), 0);
        }
    }

    /**
     * Initializing constructor
     */
    public File_processor() {
        this.init_map();
    }

    /**
     * Calculates appearances of symbols from english alphabet and writes it to symbol_container
     * @param input_file_path path to file to read from
     * @throws Is_not_file_exception when file to read is not existing or is not file
     * @throws Is_not_readable_file_exception when file to read is not readable
     * @throws Exception when there is some unexpected error
     */
    private void calculateEnglishSymbolsAppearances(String input_file_path) throws Is_not_readable_file_exception,Is_not_file_exception,Exception {
        try {
            Scanner file_reader = new Scanner(new File(input_file_path));
            while (file_reader.hasNextLine()) {
                String data = file_reader.nextLine();
                for (int i = 0; i < data.length(); i++) {
                    if(this.symbol_container.containsKey(data.charAt(i))){
                        this.symbol_container.put(data.charAt(i),this.symbol_container.get(data.charAt(i))+1);
                    }
                }
            }
            file_reader.close();
        }catch (FileNotFoundException e){
            File f = new File(input_file_path);
            if (!f.isFile()){
                throw new Is_not_file_exception("The entered path is not correct");
            }else if(!f.canRead()){
                throw new Is_not_readable_file_exception("The file cannot be opened in reading mode");
            }else{
                throw new Exception("Unexpected exception has occured");
            }

        }
    }

    /**
     * Writes result of calculating to file
     * @param output_file_path path to file, where the result will be written
     * @throws Is_not_writable_file_exception when file can`t be open for writing
     * @throws Is_not_file_exception when file to open for writing isn`t existing or is not file
     * @throws Exception when there is some unexpected error
     */
    private void write_result(String output_file_path) throws Is_not_writable_file_exception,Is_not_file_exception,Exception {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output_file_path), "utf-8"))) {
            writer.write(this.toString());
        } catch (FileNotFoundException e) {
            File f = new File(output_file_path);
            if (!f.isFile()){
                throw new Is_not_file_exception("The entered path is not correct");
            }else if(!f.canWrite()){
                throw new Is_not_writable_file_exception("The file cannot be opened in writing mode");
            }else{
                throw new Exception("Unexpected exception has occured");
            }
        }
    }

    /**
     * Process file, that user will specify and write results in file and restore symbol_container
     * @throws Exception when some exception will occur
     */
    public void process() throws Exception {
        System.out.println("Введите путь к файлу, который нужно обработать");
        Scanner in = new Scanner(System.in);
        String input_fp = in.nextLine();
        calculateEnglishSymbolsAppearances(input_fp);
        System.out.println("Введите путь к файлу, в который нужно записать результаты");
        String output_fp = in.nextLine();
        write_result(output_fp);
        init_map();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Character symbol: this.symbol_container.keySet()){
            sb.append(symbol).append(" - ").append(this.symbol_container.get(symbol)).append('\n');
        }
        return sb.toString();
    }
}
