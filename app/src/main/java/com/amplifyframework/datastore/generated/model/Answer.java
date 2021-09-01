package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Answer type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Answers")
public final class Answer implements Model {
  public static final QueryField ID = field("Answer", "id");
  public static final QueryField QUESTION_ID = field("Answer", "questionId");
  public static final QueryField DATE = field("Answer", "date");
  public static final QueryField CONTENT = field("Answer", "content");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String questionId;
  private final @ModelField(targetType="String", isRequired = true) String date;
  private final @ModelField(targetType="String", isRequired = true) String content;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getQuestionId() {
      return questionId;
  }
  
  public String getDate() {
      return date;
  }
  
  public String getContent() {
      return content;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Answer(String id, String questionId, String date, String content) {
    this.id = id;
    this.questionId = questionId;
    this.date = date;
    this.content = content;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Answer answer = (Answer) obj;
      return ObjectsCompat.equals(getId(), answer.getId()) &&
              ObjectsCompat.equals(getQuestionId(), answer.getQuestionId()) &&
              ObjectsCompat.equals(getDate(), answer.getDate()) &&
              ObjectsCompat.equals(getContent(), answer.getContent()) &&
              ObjectsCompat.equals(getCreatedAt(), answer.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), answer.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getQuestionId())
      .append(getDate())
      .append(getContent())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Answer {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("questionId=" + String.valueOf(getQuestionId()) + ", ")
      .append("date=" + String.valueOf(getDate()) + ", ")
      .append("content=" + String.valueOf(getContent()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static QuestionIdStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static Answer justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Answer(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      questionId,
      date,
      content);
  }
  public interface QuestionIdStep {
    DateStep questionId(String questionId);
  }
  

  public interface DateStep {
    ContentStep date(String date);
  }
  

  public interface ContentStep {
    BuildStep content(String content);
  }
  

  public interface BuildStep {
    Answer build();
    BuildStep id(String id) throws IllegalArgumentException;
  }
  

  public static class Builder implements QuestionIdStep, DateStep, ContentStep, BuildStep {
    private String id;
    private String questionId;
    private String date;
    private String content;
    @Override
     public Answer build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Answer(
          id,
          questionId,
          date,
          content);
    }
    
    @Override
     public DateStep questionId(String questionId) {
        Objects.requireNonNull(questionId);
        this.questionId = questionId;
        return this;
    }
    
    @Override
     public ContentStep date(String date) {
        Objects.requireNonNull(date);
        this.date = date;
        return this;
    }
    
    @Override
     public BuildStep content(String content) {
        Objects.requireNonNull(content);
        this.content = content;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String questionId, String date, String content) {
      super.id(id);
      super.questionId(questionId)
        .date(date)
        .content(content);
    }
    
    @Override
     public CopyOfBuilder questionId(String questionId) {
      return (CopyOfBuilder) super.questionId(questionId);
    }
    
    @Override
     public CopyOfBuilder date(String date) {
      return (CopyOfBuilder) super.date(date);
    }
    
    @Override
     public CopyOfBuilder content(String content) {
      return (CopyOfBuilder) super.content(content);
    }
  }
  
}
