package bg.sofia.uni.fmi.mjt.socialnetwork;

import bg.sofia.uni.fmi.mjt.socialnetwork.exception.UserRegistrationException;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.Post;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;

public class SocialNetworkImpl implements SocialNetwork {
    @Override
    public void registerUser(UserProfile userProfile) throws UserRegistrationException {

    }

    @Override
    public Set<UserProfile> getAllUsers() {
        return null;
    }

    @Override
    public Post post(UserProfile userProfile, String content) throws UserRegistrationException {
        return null;
    }

    @Override
    public Collection<Post> getPosts() {
        return null;
    }

    @Override
    public Set<UserProfile> getReachedUsers(Post post) {
        return null;
    }

    @Override
    public Set<UserProfile> getMutualFriends(UserProfile userProfile1, UserProfile userProfile2) throws UserRegistrationException {
        return null;
    }

    @Override
    public SortedSet<UserProfile> getAllProfilesSortedByFriendsCount() {
        return null;
    }
}
