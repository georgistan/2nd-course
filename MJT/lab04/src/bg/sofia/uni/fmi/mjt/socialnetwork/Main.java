package bg.sofia.uni.fmi.mjt.socialnetwork;

import bg.sofia.uni.fmi.mjt.socialnetwork.exception.UserRegistrationException;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.DefaultUserProfile;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.Interest;

public class Main {
    public static void main(String[] args) throws UserRegistrationException {
        DefaultUserProfile user1 = new DefaultUserProfile("georgistan");
        DefaultUserProfile user2 = new DefaultUserProfile("mariovlad");
        DefaultUserProfile user3 = new DefaultUserProfile("gosho");
        DefaultUserProfile user4 = new DefaultUserProfile("stefan");
        DefaultUserProfile user5 = new DefaultUserProfile("krido");
        Interest interestMusic = Interest.MUSIC;
        Interest interestSports = Interest.SPORTS;

        user1.addFriend(user3);
        user1.addFriend(user4);
        user1.addFriend(user5);
        user1.addInterest(interestMusic);
        user1.addInterest(interestSports);

        user2.addFriend(user4);
        user2.addFriend(user3);

        SocialNetworkImpl socialNetwork = new SocialNetworkImpl();
        socialNetwork.registerUser(user1);
        socialNetwork.registerUser(user2);
        socialNetwork.registerUser(user3);
        socialNetwork.registerUser(user4);

        socialNetwork.getMutualFriends(user1, user2);
    }
}
