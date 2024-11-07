package bg.sofia.uni.fmi.mjt.socialnetwork;

import bg.sofia.uni.fmi.mjt.socialnetwork.profile.DefaultUserProfile;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.Interest;

import java.util.Collection;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        DefaultUserProfile user1 = new DefaultUserProfile("georgistan");
        DefaultUserProfile user2 = new DefaultUserProfile("mariovlad");
        Interest interestMusic = Interest.MUSIC;
        Interest interestSports = Interest.SPORTS;

        user1.addFriend(user2);
        user1.addInterest(interestMusic);
        user1.addInterest(interestSports);

        System.out.println(user1.getInterests());
    }
}
