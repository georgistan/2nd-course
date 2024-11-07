package bg.sofia.uni.fmi.mjt.socialnetwork;

import bg.sofia.uni.fmi.mjt.socialnetwork.exception.UserRegistrationException;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.Post;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.SocialFeedPost;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;

public class SocialNetworkImpl implements SocialNetwork {
    private Set<UserProfile> users;
    private Set<Post> posts;

    {
        users = new HashSet<>();
        posts = new HashSet<>();
    }

    @Override
    public void registerUser(UserProfile userProfile) throws UserRegistrationException {
        if (userProfile == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (users.contains(userProfile)) {
            throw new UserRegistrationException("User is already registered");
        }

        users.add(userProfile);
    }

    @Override
    public Set<UserProfile> getAllUsers() {
        return Set.copyOf(users);
    }

    @Override
    public Post post(UserProfile userProfile, String content) throws UserRegistrationException {
        if (userProfile == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null");
        }

        if (!users.contains(userProfile)) {
            throw new UserRegistrationException("User is not registered");
        }

        SocialFeedPost newPost = new SocialFeedPost(userProfile, content);
        posts.add(newPost);

        return newPost;
    }

    @Override
    public Collection<Post> getPosts() {
        return Set.copyOf(posts);
    }

    @Override
    public Set<UserProfile> getReachedUsers(Post post) {
        return null;
    }

    @Override
    public Set<UserProfile> getMutualFriends(UserProfile userProfile1, UserProfile userProfile2)
        throws UserRegistrationException {
        if (userProfile1 == null || userProfile2 == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (!users.contains(userProfile1) || !users.contains(userProfile2)) {
            throw new UserRegistrationException("User is not registered.");
        }

        Collection<UserProfile> user1Friends = userProfile1.getFriends();

        user1Friends.retainAll(userProfile2.getFriends());

        return new HashSet<>(user1Friends);
    }

    @Override
    public SortedSet<UserProfile> getAllProfilesSortedByFriendsCount() {
        return null;
    }
}
