package loginapp;

public enum option {
    Admin, Student;

    private option(){}

    public String value(){
        return name();
    }

    public static option fromvalues(String v){
        return valueOf(v);
    }
}
