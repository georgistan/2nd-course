package bg.sofia.uni.fmi.mjt.socialnetwork.post;

import bg.sofia.uni.fmi.mjt.socialnetwork.profile.UserProfile;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class SocialFeedPost implements Post {
    private String id;
    private UserProfile author;
    private String content;
    private LocalDateTime publishDate;
    private Map<UserProfile, ReactionType> reactions;

    public SocialFeedPost(UserProfile author, String content) {
        this.id = UUID.randomUUID().toString();
        this.author = author;
        this.content = content;
        this.publishDate = LocalDateTime.now();
        this.reactions = new HashMap<>();
    }

    @Override
    public String getUniqueId() {
        return this.id;
    }

    public UserProfile getAuthor() {
        return this.author;
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public LocalDateTime getPublishedOn() {
        return this.publishDate;
    }

    @Override
    public boolean addReaction(UserProfile userProfile, ReactionType reactionType) {
        if (userProfile == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        if (reactionType == null) {
            throw new IllegalArgumentException("Reaction type cannot be null.");
        }

        if (reactions.containsKey(userProfile)) {
            reactions.replace(userProfile, reactionType);

            return false;
        }

        reactions.put(userProfile, reactionType);

        return true;
    }

    @Override
    public boolean removeReaction(UserProfile userProfile) {
        if (userProfile == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        if (!reactions.containsKey(userProfile)) {
            return false;
        }

        reactions.remove(userProfile);

        return true;
    }

    @Override
    public Map<ReactionType, Set<UserProfile>> getAllReactions() {
        Collection<ReactionType> values = reactions.values();
        Set<Map.Entry<UserProfile, ReactionType>> pairs = reactions.entrySet();

        Map<ReactionType, Set<UserProfile>> result = new HashMap<>();

        for (ReactionType reactionType : values) {
            Set<UserProfile> usersWithReaction = new HashSet<>();

            for (Map.Entry<UserProfile, ReactionType> pair : pairs) {
                if (reactionType.equals(pair.getValue())) {
                    usersWithReaction.add(pair.getKey());
                }
            }

            result.put(reactionType, usersWithReaction);
        }

        return Map.copyOf(result);
    }

    @Override
    public int getReactionCount(ReactionType reactionType) {
        if (reactionType == null) {
            throw new IllegalArgumentException("Reaction type cannot be null.");
        }

        int result = 0;

        Collection<ReactionType> values = reactions.values();

        for (ReactionType reaction : values) {
            if (reaction.equals(reactionType)) {
                result++;
            }
        }

        return result;
    }

    @Override
    public int totalReactionsCount() {
        return reactions.size();
    }
}
