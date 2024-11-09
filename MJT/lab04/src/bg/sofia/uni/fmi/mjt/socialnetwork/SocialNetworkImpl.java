package bg.sofia.uni.fmi.mjt.socialnetwork;

import bg.sofia.uni.fmi.mjt.socialnetwork.exception.UserRegistrationException;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.Post;
import bg.sofia.uni.fmi.mjt.socialnetwork.post.SocialFeedPost;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.Interest;
import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class SocialNetworkImpl implements SocialNetwork {
    private Set<UserProfile> users;
    private Set<Post> posts;

    {
        users = new HashSet<>();
        posts = new HashSet<>();
    }

    private boolean anyInterestIsCommon(Collection<Interest> authorInterests, Collection<Interest> userInterests) {
        for (Interest interest : authorInterests) {
            if (userInterests.contains(interest)) {
                return true;
            }
        }

        return false;
    }

    private boolean areUsersInFriendNetwork(UserProfile user1, UserProfile user2) {
        if (!user1.isFriend(user2)) {
            return false;
        }

        return false;
    }

    @Override
    public void registerUser(UserProfile userProfile) throws UserRegistrationException {
        if (userProfile == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        if (users.contains(userProfile)) {
            throw new UserRegistrationException("User is already registered.");
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
            throw new IllegalArgumentException("User cannot be null.");
        }

        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty.");
        }

        if (!users.contains(userProfile)) {
            throw new UserRegistrationException("User is not registered.");
        }

        Post newPost = new SocialFeedPost(userProfile, content);
        posts.add(newPost);

        return newPost;
    }

    @Override
    public Collection<Post> getPosts() {
        return Set.copyOf(posts);
    }

    // работихме заедно с един от колегите, за да измислим логиката тук, ако ви се струва твърде еднакъв кодът :)
    @Override
    public Set<UserProfile> getReachedUsers(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("Post cannot be null.");
        }

        Set<UserProfile> reachedUsers = new HashSet<>();
        Set<UserProfile> curr = new HashSet<>(post.getAuthor().getFriends());

        while (!curr.isEmpty()) {
            Set<UserProfile> next = new HashSet<>();

            for (UserProfile userProfile : curr) {
                Set<Interest> commonInterests = new HashSet<>(userProfile.getInterests());
                commonInterests.retainAll(post.getAuthor().getInterests());

                if (!commonInterests.isEmpty() && !reachedUsers.contains(userProfile)) {
                    reachedUsers.add(userProfile);
                    next.addAll(userProfile.getFriends());
                }
            }

            curr = next;
        }

        return reachedUsers;
    }

    @Override
    public Set<UserProfile> getMutualFriends(UserProfile userProfile1, UserProfile userProfile2)
        throws UserRegistrationException {
        if (userProfile1 == null || userProfile2 == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        if (!users.contains(userProfile1) || !users.contains(userProfile2)) {
            throw new UserRegistrationException("User must be registered.");
        }

        Collection<UserProfile> user1Friends = userProfile1.getFriends();

        Set<UserProfile> mutualFriends = new HashSet<>();

        for (UserProfile friend : user1Friends) {
            if (userProfile2.isFriend(friend)) {
                mutualFriends.add(friend);
            }
        }

        return mutualFriends;
    }

    @Override
    public SortedSet<UserProfile> getAllProfilesSortedByFriendsCount() {
        return new TreeSet<>(users).descendingSet();
    }
}
