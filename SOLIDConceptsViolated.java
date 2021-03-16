package ConceptsDemo;

/**
 *
 *  This class Violates  SOLID Principles
 *
 **/


import java.util.ArrayList;
import java.util.List;


class Task{
    protected int taskID;
    protected String taskDescription;
    protected boolean isDone;
    // required new field - 2nd modification violating OC
    protected String imageUrl;

    // constructor
    Task(int taskID, String taskDescription,String imageUrl){
            this.taskID = taskID;
            this.taskDescription = taskDescription;
            this.isDone = false;
            // 2nd modification
            this.imageUrl = imageUrl;
    }

    public void taskCompleted(){
        this.isDone = true;
    }

    // show image
    // 2nd modification
    public void renderImage(){
        System.out.println(this.imageUrl);
    }


}

class Issue extends Task{

    Issue(int taskID, String taskDescription, String imageUrl) {
        super(taskID, taskDescription, imageUrl);
    }

    // violation of L
    @Override
    public void taskCompleted() {
        // super.taskCompleted();
        // some code cannot mark task incomplete.
    }
}


class SqlDBInstance{
    public void saveTasksToDB(List<Task> tasks){
        // handles saving to SQl DB
        System.out.println("Saved to SQL Database");
    }

}
class MongoDBInstance{
    public void saveTasksToDB(List<Task> tasks){
        // handles saving to Mongo DB
        System.out.println("Saved to mongo DB");
    }
}

class TaskManager{

    private final List<Task> taskList = new ArrayList<>();

    // above taskList and issueList are not same
    // private final List<Issue> issueList = new ArrayList<>();

    // handles printing all the tasks.
    public void printAllTasks(){
        for(Task task : taskList){
            System.out.println("ID : "+task.taskID+
                        "\nDesc: " +task.taskDescription+
                        "\nisDone: "+task.isDone+
                    // 2nd modification
                        "\nImage URL: "+task.imageUrl+"\n\n");
        }
    }

    // handles removing all tasks
    public void removeAllTasks(){
        taskList.clear();
    }

    // handles saving to db
    public void saveListToDB(){
        // deals with saving to the SQL db.
        final SqlDBInstance sqlDBInstance = new SqlDBInstance();
        sqlDBInstance.saveTasksToDB(taskList);

        // deals with saving to Mongo DB.
        // final DBInstance mongoDBInstance = new MongoDBInstance();
        // mongoDBInstance.saveTasksToDB(taskList);

        // increase of tight coupling with db instance
        // since does not need to depend on the low level code.

    }

    // violating single responsibility

    // handles adding a new task.
    public void addNewTask(Task newTask){
        taskList.add(newTask);
    }

    // handles marking a task completed.
    public void markTaskComplete(int completedTaskID){
        for(Task task:taskList){
            if(task.taskID == completedTaskID) {
                task.taskCompleted();
                break;
            }
        }
    }

    // handles editing description of the task
    public void editTaskDescription(int editTaskID, String editedDescription){
        for(Task task:taskList){
            if(task.taskID == editTaskID) {
                task.taskDescription = editedDescription;
                break;
            }
        }
    }


}

// problems with above code
// violation of SRP
// TaskManager Class is Responsible for storing the tasks and editing & modifying individual task

// violation of OC
// In order to change the image field change in Task class , needed to modify the existing code
// and it breaks the existing class which uses Task class.

// violation of L
// Although Issue class is extends the Task class, Issue class cannot be used as substitute for Task class
// since the Issue class is breaking the functionality of taskCompleted function

// Violation of IS
// there is no way of differentiate a Task with Image and Other with no image

// violation of DI
// there is a tight coupling with the database saving operation.
// creating new instance and calling the class function.

public class SOLIDConceptsViolated {
    public static void main(String[] args){
        TaskManager taskManager = new TaskManager();
        taskManager.addNewTask(new Task(101,"task 1",null));
        taskManager.addNewTask(new Task(102,"task 2",null));
        taskManager.addNewTask(new Issue(103,"task with issue","https://imgae.com"));
        taskManager.markTaskComplete(103);
        taskManager.markTaskComplete(101);
        taskManager.printAllTasks();
        taskManager.editTaskDescription(102,"Modified Task");
        taskManager.printAllTasks();
        taskManager.saveListToDB();
    }
}





