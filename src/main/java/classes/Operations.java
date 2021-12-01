package classes;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Class Operations hold all methods for operating on tasks in TaskManager
 * @class Operations
 */
public class Operations {

    private String[] optionList = {"add", "remove", "list", "exit"};
    private Task[] tasksTable = new Task[0];
    private String fileName;


    public Operations(String fileName){
        this.fileName= fileName;
        run(fileName);
    }
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /*
        Run method operates the logic of whole program
    */
    private void run(String filename) {
        readFile(filename);

        while(true){
            System.out.println(ConsoleColors.BLUE + "Please select an option" + ConsoleColors.RESET);
            for(int i = 0; i < optionList.length; i++){
                System.out.println(optionList[i]);
            }
            Scanner scanner = new Scanner(System.in);
            String inscription = scanner.nextLine();

            switch(inscription) {
                case "add": {
                    add();
                    break;
                }
                case "remove": {
                    remove();
                    break;
                }
                case "list": {
                    list();
                    break;
                }
                case "exit": {
                    exit();
                    break;
                }
            }
        }
    }

    public void add(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Put task inscription: ");
        String inscription = scanner.nextLine();
        System.out.println("Put to which date task should be done: (yyyy-mm-dd) ");
        String date = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate taskTime = LocalDate.now();
        while(true){
            date = scanner.nextLine();
            try{
              taskTime = LocalDate.parse(date, formatter);
              if(taskTime.isAfter(LocalDate.now())) break;
              else System.out.println("The date must be in future, please put valid value: ");
            }catch(Exception exc){
                System.out.println("Invalid value, put date like yyyy-mm-dd: ");    ;
            }
        }

        System.out.println("Is task important? true/false");
        String isImportant = "";
        while(true){
                isImportant = scanner.nextLine();
                if(isImportant.equals("true")|| isImportant.equals("false")) break;
                System.out.println("Invalid value, put true or false");
        }
        tasksTable = Arrays.copyOf(tasksTable, tasksTable.length+1);
        tasksTable[tasksTable.length-1] = new Task(inscription,taskTime,Boolean.getBoolean(isImportant));
    }

    public void remove(){
        System.out.println("Please tell me which task would you like to remove (which one is it in line?) : ");
        Scanner scanner = new Scanner(System.in);
        while(!scanner.hasNextInt()){
            System.out.println("Invalid value, put the numeric type, please: ");
            scanner.nextLine();
        }
        int index = scanner.nextInt() - 1;
        if(index < tasksTable.length) {
            Task[] taskArray = new Task[tasksTable.length - 1];
            for (int i = 0, k = 0; i < tasksTable.length; i++) {
                if (i == index) {
                    continue;
                }
                taskArray[k++] = tasksTable[i];
            }
            tasksTable = Arrays.copyOf(taskArray, taskArray.length);
        }
        else System.out.println("There is no such task with given number");
    }

    public void list(){
        for(int i = 0; i < tasksTable.length; i++){
            System.out.println(String.format("%d : %s %s %s",i+1,tasksTable[i].getInscription(),tasksTable[i].getDueToDate(),tasksTable[i].getIsImportant()));
        }
    }

    public String[] getOptionList() {
        return optionList;
    }

    public void setOptionList(String[] optionList) {
        this.optionList = optionList;
    }

    public Task[] getTasksTable() {
        return tasksTable;
    }

    public void setTasksTable(Task[] tasksTable) {
        this.tasksTable = tasksTable;
    }

    public void exit(){
        saveToFile(tasksTable);
        System.out.println(ConsoleColors.RED + "Bye, bye.");
        System.exit(0);
    }

    private void saveToFile(Task[] tasksTable) {
        Path path = Paths.get(fileName);
        if(!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try(FileWriter fileWriter = new FileWriter(String.valueOf(path))){
            Task[] tmpTable = getTasksTable();
            for(int i = 0; i< tmpTable.length; i++){
                fileWriter.append(tmpTable[i].toString()).append("\n");
            }
        }catch(IOException exc){
            System.out.println(exc.getMessage());
        }

    }

    private void readFile(String fileName){
        Path path = Paths.get(fileName);
        if(Files.exists(path)){
            try(Scanner scanner = new Scanner(path)) {
                while(scanner.hasNextLine()){
                    String task = scanner.nextLine();
                    String[] values = task.split(",");
                    tasksTable = Arrays.copyOf(tasksTable, tasksTable.length+1);
                    tasksTable[tasksTable.length-1] = new Task(values[0], LocalDate.parse(values[1]), Boolean.getBoolean(values[2]));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
