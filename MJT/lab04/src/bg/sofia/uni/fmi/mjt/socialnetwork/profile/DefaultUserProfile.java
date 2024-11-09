package bg.sofia.uni.fmi.mjt.socialnetwork.profile;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class DefaultUserProfile implements UserProfile, Comparable<UserProfile> {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultUserProfile that = (DefaultUserProfile) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public int compareTo(UserProfile o) {
        if (this.username.equals(o.getUsername())) {
            return 0;
        }

        if (this.friends.size() > o.getFriends().size()) {
            return 1;
        } else if (this.friends.size() < o.getFriends().size()) {
            return -1;
        }

        return 1;
    }

    @Override
    public Collection<Interest> getInterests() {
        return Set.copyOf(interests);
    }

    @Override
    public boolean addInterest(Interest interest) {
        if (interest == null) {
            throw new IllegalArgumentException("Interest cannot be null");
        }

        if (interests.contains(interest)) {
            return false;
        }

        interests.add(interest);

        return true;
    }

    @Override
    public boolean removeInterest(Interest interest) {
        if (interest == null) {
            throw new IllegalArgumentException("Interest cannot be null.");
        }

        if (!interests.contains(interest)) {
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
        if (userProfile == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        if (userProfile == this) {
            throw new IllegalArgumentException("User cannot add themselves as a friend.");
        }

        if (this.isFriend(userProfile)) {
            return false;
        }

        friends.add(userProfile);
        userProfile.addFriend(this);

        return true;
    }

    @Override
    public boolean unfriend(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        if (!friends.contains(userProfile)) {
            return false;
        }

        friends.remove(userProfile);
        userProfile.unfriend(this);

        return true;
    }

    @Override
    public boolean isFriend(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        return this.friends.contains(userProfile);
    }
}
