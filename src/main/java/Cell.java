public  enum Cell {
    VOID,BORDER,SPACE;

    @Override
    public String toString() {
        if (VOID == this)return "/";
        else if(BORDER == this)return "+";
        else if (SPACE == this)return ".";
        return " ";
    }
}
