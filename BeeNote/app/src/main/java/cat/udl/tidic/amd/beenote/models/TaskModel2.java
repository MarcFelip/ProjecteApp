package cat.udl.tidic.amd.beenote.models;

import cat.udl.tidic.amd.beenote.R;

//private enum TaskType{
//        Classe("classe", "classe"), Entrega("entrega", "entrega"), Examen("examen", "examen");
//
//        String name;
//        String id;
//
//        TaskType(String _id,String name){
//            this.name = name;
//            this.id = id;
//        }
//
//        public static Integer getColourResource(TaskType t){
//        switch(t){
//            case Classe:
//                return R.color.red;
//            case Entrega:
//                return R.color.colorAccent;
//            case Examen:
//                return R.color.colorPrimary;
//            default:
//                return -1;
//        }
//    }
//
//        // TODO: Si voleu assignar una icona a cada tipo
//}


public class TaskModel2 {

    private String tittle;
    private String details;
    private String deadline;
    private int total_points;

    public TaskModel2() {
    }

    public TaskModel2(String name, String description, String deadline,int total_points) {
        this.tittle = name;
        this.details = description;
        this.deadline = deadline;
        this.total_points = total_points;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getTotal_points() {
        return total_points;
    }

    public void setTotal_points(int total_points) {
        this.total_points = total_points;
    }
}
