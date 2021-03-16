    package ConceptsDemo;

    import java.util.ArrayList;
    import java.util.List;

    /**
     *  This class Applies SOLID Principles
     * */

    // interface segregation
    interface TaskInterface{
        void taskCompleted();
    }

    interface TaskWithImageInterface{
         void renderImage();
    }


    class TaskM implements TaskInterface{
        protected int taskID;
        protected String taskDescription;
        protected boolean isDone;

        // constructor
        TaskM(int taskID, String taskDescription){
            this.taskID = taskID;
            this.taskDescription = taskDescription;
            this.isDone = false;
        }

        @Override
        public void taskCompleted(){
            this.isDone = true;
        }

    }

    class TaskWithImage extends TaskM implements TaskWithImageInterface{

        protected String imageURL;
        TaskWithImage(int taskID, String taskDescription,String imageURL) {
            super(taskID, taskDescription);
            this.imageURL = imageURL;
        }

        @Override
        public void renderImage(){
            System.out.println(this.imageURL);
        }
    }

    // Use of Liskov principle.
    class IssueM extends TaskM{

        IssueM(int taskID, String taskDescription) {
            super(taskID, taskDescription);
        }

        public void taskCompleted() {
            super.taskCompleted();
            System.out.println("Task Marked as completed");
        }
    }

    // Use of Dependency Inversion Principle
    interface DBInstance{
         void saveTasksToDB(List<TaskM> tasks);
    }

    class SqlDBInstanceM implements DBInstance{
        public void saveTasksToDB(List<TaskM> tasks){
            // handles saving to SQl DB
            System.out.println("\nSaved to SQL Database\n");
        }

    }
    class MongoDBInstanceM implements DBInstance{
        public void saveTasksToDB(List<TaskM> tasks){
            // handles saving to Mongo DB
            System.out.println("\nSaved to mongo DB\n");
        }
    }
    class SaveDatabase{
        private final DBInstance dbInstance;

        // constructor
        SaveDatabase(){
           // this.dbInstance = new SqlDBInstanceM();
            this.dbInstance = new MongoDBInstanceM();
        }

        // method to call
        void saveToDB(List<TaskM> tasks){
            dbInstance.saveTasksToDB(tasks);
        }

    }

    class TaskEditor{
        // handles marking task as complete
        public void markTaskComplete(int completedTaskID,List<TaskM> taskList){
            for(TaskM task:taskList){
                if(task.taskID == completedTaskID) {
                    task.taskCompleted();
                    break;
                }
            }
        }

        // handles editing description of the task
        public void editTaskDescription(int editTaskID, String editedDescription,List<TaskM> taskList){
            for(TaskM task:taskList){
                if(task.taskID == editTaskID) {
                    task.taskDescription = editedDescription;
                    break;
                }
            }
        }
    }

    class TaskManagerM{

        private final List<TaskM> taskList = new ArrayList<>();

        // handles printing all the tasks.
        public List<TaskM> getTaskList(){
            return taskList;
        }

        public void printAllTasks(){
            if(taskList.isEmpty()){
                System.out.println("\nTask List is Empty\n");
                return;
            }
            for(TaskM task : taskList){
                System.out.println("ID : "+task.taskID+
                        "\nDesc: " +task.taskDescription+
                        "\nisDone: "+task.isDone+"\n\n");
            }
        }

        // handles removing all tasks
        public void removeAllTasks(){
            taskList.clear();
        }

        // handles saving to db
        public void saveTasksToDB(){
            final SaveDatabase saveDatabase = new SaveDatabase();
            saveDatabase.saveToDB(taskList);

        }

        // adds new task
        public void addNewTask(TaskM newTask){
            taskList.add(newTask);
        }

    }



    public class SOLIDConceptsApplied {
        public static void main(String[] args){

            TaskManagerM taskManager = new TaskManagerM();
            TaskEditor taskEditor = new TaskEditor();

            // creating task Objects
            taskManager.addNewTask(new TaskM(101,"task 1"));
            taskManager.addNewTask(new TaskM(102,"task 2"));
            // Fulfilling the Liskov Principle
            taskManager.addNewTask(new IssueM(103,"task with issue"));

            // print newly created tasks
            taskManager.printAllTasks();

            // Mark the Task As Complete (with SRP)
            taskEditor.markTaskComplete(103,taskManager.getTaskList());
            taskEditor.markTaskComplete(101,taskManager.getTaskList());

            // Edit the Task Description.
            taskEditor.editTaskDescription(102,"Modified Task",taskManager.getTaskList());

            // printing all Tasks and observe the changes.
            taskManager.printAllTasks();

            // save all Tasks to database with D (principle)
            taskManager.saveTasksToDB();

            // remove all tasks from the List
            taskManager.removeAllTasks();

            taskManager.printAllTasks();
        }
    }
