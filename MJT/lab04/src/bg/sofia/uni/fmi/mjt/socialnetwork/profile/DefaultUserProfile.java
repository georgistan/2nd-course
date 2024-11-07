package bg.sofia.uni.fmi.mjt.socialnetwork.profile;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DefaultUserProfile implements UserProfile {

    // username uniqueness!!!
    // what type of collections for interests and friends???


    private String username;
    private Set<Interest> interests;
    private Set<UserProfile> friends;

    public DefaultUserProfile(String username) {
        this.username = username;
        interests = new HashSet<>();
        friends = new HashSet<>();
    }

    public String getUsername() {
        return username;
    }

    @Override
    public Collection<Interest> getInterests() {
        return Set.copyOf(interests);
    }

    @Override
    public boolean addInterest(Interest interest) {
        if(interest == null) {
            throw new IllegalArgumentException("Interest cannot be null");
        }

        if(interests.contains(interest)) {
            return false;
        }

        interests.add(interest);

        return true;
    }

    @Override
    public boolean removeInterest(Interest interest) {
        if(interest == null) {
            throw new IllegalArgumentException("Interest cannot be null.");
        }

        if(!interests.contains(interest)) {
            return false;
        }

        interests.remove(interest);

        return true;
    }

    @Override
    public Collection<UserProfile> getFriends() {
        return Set.copyOf(friends);
    }

    @Override
    public boolean addFriend(UserProfile userProfile) {
        if(userProfile == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        if(userProfile == this) {
            throw new IllegalArgumentException("User cannot add themselves as a friend.");
        }

        if(userProfile.isFriend(this)) {
            return false;
        }

        friends.add(userProfile);
        userProfile.addFriend(this);

        return true;
    }

    @Override
    public boolean unfriend(UserProfile userProfile) {
        if(userProfile == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        if(!friends.contains(userProfile)) {
            return false;
        }

        friends.remove(userProfile);
        userProfile.unfriend(this);

        return true;
    }

    @Override
    public boolean isFriend(UserProfile userProfile) {
        if(userProfile == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        return friends.contains(userProfile);
    }
}
