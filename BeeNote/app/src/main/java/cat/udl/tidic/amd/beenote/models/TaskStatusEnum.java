package cat.udl.tidic.amd.beenote.models;


import cat.udl.tidic.amd.beenote.R;

public enum TaskStatusEnum {
    Completed("Completed","Completed"),
    Ongoing("Ongoing","Ongoing"),
    Closed("Closed","Closed");

    String name="";
    String id;

    TaskStatusEnum(String _id, String _name){
        id = _id;
        name = _name;
    }

    public String getName(){
        return name;
    }

    public static int getColourResource(TaskStatusEnum e){

        switch(e){
            case Completed:
                return R.color.blue;
            case Ongoing:
                return R.color.green;
            case Closed:
                return R.color.red2;
            default:
                return -1;
        }

    }
}
