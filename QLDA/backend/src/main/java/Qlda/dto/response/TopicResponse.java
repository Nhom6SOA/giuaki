package Qlda.dto.response;

import Qlda.entity.Topic;

public record TopicResponse(Long id, String topicCode, String title, String description,
                            String supervisor, Integer maxStudents, String status) {
    public static TopicResponse from(Topic t) {
        return new TopicResponse(t.getId(), t.getTopicCode(), t.getTitle(), t.getDescription(),
                t.getSupervisor(), t.getMaxStudents(), t.getStatus());
    }
}