public class starterRestaurant extends Restaurant{
    @Override
    public Course createCourse(){
        return new starterCourse();
    }
}
