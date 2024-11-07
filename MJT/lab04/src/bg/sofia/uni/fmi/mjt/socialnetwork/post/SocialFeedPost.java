package bg.sofia.uni.fmi.mjt.socialnetwork.post;

import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

public class SocialFeedPost implements Post {
    private UserProfile author;
    private String content;

    public SocialFeedPost(UserProfile author, String content){
        this.author = author;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public UserProfile getAuthor() {
        return author;
    }

    @Override
    public String getUniqueId() {
        return null;
    }

    @Override
    public LocalDateTime getPublishedOn() {
        return null;
    }

    @Override
    public boolean addReaction(UserProfile userProfile, ReactionType reactionType) {
        return false;
    }

    @Override
    public boolean removeReaction(UserProfile userProfile) {
        return false;
    }

    @Override
    public Map<ReactionType, Set<UserProfile>> getAllReactions() {
        return null;
    }

    @Override
    public int getReactionCount(ReactionType reactionType) {
        return 0;
    }

    @Override
    public int totalReactionsCount() {
        return 0;
    }
}
