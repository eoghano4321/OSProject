public class desertRestaurant extends Restaurant{
    @Override
    public Course createCourse(){
        return new desertCourse();
    }
}
