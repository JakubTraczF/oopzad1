
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Person {
    private final String name;
    private final LocalDate birthDate;
    private final LocalDate deathDate;
    private final List<Person> parents;

    public Person(String name, LocalDate birthDate, LocalDate deathDate){
        this.name = name;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.parents = new ArrayList<>();
    }
    public void addParent(Person person){
        parents.add(person);
    }
    public static Person fromCsvLine(String line){
        String[] parts = line.split(",", -1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthDate = LocalDate.parse(parts[1], formatter);
        LocalDate deathDate = (parts[2].isEmpty()) ? null : LocalDate.parse(parts[2], formatter);
        return new Person(parts[0], birthDate, deathDate);
    }
    public static List<Person> fromCsv(String path){
        List<Person> personList = new ArrayList<>();
        Map<String, PersonWithParentsNames> personWithParentsNamesMap = new HashMap<>();
        String line;
        BufferedReader bufferedReader = null;
        FileReader fileReader = null;
        try{
            fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine();
            while((line = bufferedReader.readLine()) != null){
                PersonWithParentsNames personWithParentsNames = PersonWithParentsNames.fromCsvLine(line);
                Person person = personWithParentsNames.getPerson();
                try{
                    person.lifespanValidate();
                    personList.add(person);
                    personWithParentsNamesMap.put(person.name, personWithParentsNames);
                }catch (NegativeLifespanExeption e){
                    System.err.println(e.getMessage());
                }
            }
            PersonWithParentsNames.fillParents(personWithParentsNamesMap);
        } catch (IOException e){
            System.out.println(e.getMessage());
        } finally {
            if(bufferedReader != null){
                try{
                    bufferedReader.close();
                }catch (IOException e){
                    System.out.println(e.getMessage());
                }
            }
        }
        return personList;
    }
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", deathDate=" + deathDate +
                ", parents=" + parents +
                '}';
    }
    public void lifespanValidate() throws NegativeLifespanExeption{
        if(this.deathDate != null && this.deathDate.isBefore(this.birthDate)){
            throw new NegativeLifespanExeption(this);
        }
    }
}
