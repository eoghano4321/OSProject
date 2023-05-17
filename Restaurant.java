public abstract class Restaurant {
    public Course orderCourse(){
        Course course = createCourse();
        course.prepare();
        return course;
    }
    
    public abstract Course createCourse();

}
