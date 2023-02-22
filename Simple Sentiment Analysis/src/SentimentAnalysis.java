import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SentimentAnalysis {
    public static void main(String [] args){
        try {
            //simply taking in the file
            FileInputStream file = new FileInputStream("sentiments.txt");
            Scanner in = new Scanner(file);
            HashTable sentiments = new HashTable<>(50);
            //the sentiments.txt has a format of "(word),(value)" or "(word)(word),(value)"
            //so this way of splitting the word up works perfectly fine for our has keys and values
            while (in.hasNextLine()){
                String[] daValues=  in.nextLine().split(",");
                sentiments.insert(daValues[0],daValues[1]);
            }
            Scanner uin = new Scanner(System.in);
            System.out.println("Enter text:");
            String userinput="";
            String curword="";
            //keeps adding the previous as long as it isn't 'END'
            while (!curword.equals("END")){
                userinput+=(curword+" ");
                curword=uin.next();
            }
            System.out.println();
            //makes the input lowercase
            String paragraph = userinput.toLowerCase();
            //splits the input by sentances
            String[] sentences = paragraph.split("\\.");
            //starts sentiment counter at 0
            int totalSentiment=0;
            //starts word counter at -1 because the paragraph starts with an empty string
            int totalWords=-1;
            for (String s : sentences){
                //i check two words, then the single word after.
                //so this allows the loop to run one word longer in case
                //the last user inputted word is in the hash
                s= s+" paddingString";
                totalWords--;
                //remove whitespace and put into an array
                String[] words = s.split(" ");
                //this is to not allow two elements
                boolean skip=false;
                //loop through every word and stop when the array is two less than the length
                //so the loop can get the two elements at the time
                for (int i=0; i<words.length-1;i++){
                    String checkword=words[i+1];
                    //removes any non lower case character
                    if (checkword.length()>0) {
                        char lastChar = checkword.charAt(checkword.length() - 1);
                        if (!(lastChar>60 && lastChar<123)){
                            words[i+1]=checkword.substring(0,checkword.length()-1);
                        }
                    }
                    //checks if the two words are in the hash, if not, it checks the single word
                    //else, it'll just increment the word counter
                    if ((sentiments.lookup(words[i]+" "+words[i+1])!=null)&&skip==false){
                        String varr =(String) sentiments.lookup(words[i]+" "+words[i+1]);
                        totalSentiment+=Integer.parseInt(varr);
                        totalWords++;
                        skip=true;
                    }
                    else
                        if ((sentiments.lookup(words[i]))!=null && skip==false){
                            String var = (String) sentiments.lookup(words[i]);
                            totalSentiment+=Integer.parseInt(var);
                            totalWords++;
                        }
                        else {
                            totalWords++;
                            skip=false;
                        }
                }
            }
            //prints all the required output statements
            System.out.println("Words: "+totalWords);
            System.out.println("Sentiment: "+totalSentiment);
            double averageSent=(double)totalSentiment/ (double)totalWords;
            System.out.printf("Overall: %.2f",averageSent);

        }
        catch (FileNotFoundException e){
            System.out.println("File could not be found!");
        }
    }
}
