package src;
public class mainRestaurant extends Restaurant{
    @Override
    public Course createCourse(){
        return new mainCourse();
    }
}

