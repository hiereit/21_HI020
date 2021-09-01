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

/** This is an auto generated class representing the Question type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Questions")
public final class Question implements Model {
  public static final QueryField ID = field("Question", "id");
  public static final QueryField TITLE = field("Question", "title");
  public static final QueryField CONTENT = field("Question", "content");
  public static final QueryField DATE = field("Question", "date");
  public static final QueryField USER_ID = field("Question", "userId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="String", isRequired = true) String content;
  private final @ModelField(targetType="String", isRequired = true) String date;
  private final @ModelField(targetType="String", isRequired = true) String userId;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getTitle() {
      return title;
  }
  
  public String getContent() {
      return content;
  }
  
  public String getDate() {
      return date;
  }
  
  public String getUserId() {
      return userId;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Question(String id, String title, String content, String date, String userId) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.date = date;
    this.userId = userId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Question question = (Question) obj;
      return ObjectsCompat.equals(getId(), question.getId()) &&
              ObjectsCompat.equals(getTitle(), question.getTitle()) &&
              ObjectsCompat.equals(getContent(), question.getContent()) &&
              ObjectsCompat.equals(getDate(), question.getDate()) &&
              ObjectsCompat.equals(getUserId(), question.getUserId()) &&
              ObjectsCompat.equals(getCreatedAt(), question.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), question.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTitle())
      .append(getContent())
      .append(getDate())
      .append(getUserId())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Question {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("content=" + String.valueOf(getContent()) + ", ")
      .append("date=" + String.valueOf(getDate()) + ", ")
      .append("userId=" + String.valueOf(getUserId()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TitleStep builder() {
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
  public static Question justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Question(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      title,
      content,
      date,
      userId);
  }
  public interface TitleStep {
    ContentStep title(String title);
  }
  

  public interface ContentStep {
    DateStep content(String content);
  }
  

  public interface DateStep {
    UserIdStep date(String date);
  }
  

  public interface UserIdStep {
    BuildStep userId(String userId);
  }
  

  public interface BuildStep {
    Question build();
    BuildStep id(String id) throws IllegalArgumentException;
  }
  

  public static class Builder implements TitleStep, ContentStep, DateStep, UserIdStep, BuildStep {
    private String id;
    private String title;
    private String content;
    private String date;
    private String userId;
    @Override
     public Question build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Question(
          id,
          title,
          content,
          date,
          userId);
    }
    
    @Override
     public ContentStep title(String title) {
        Objects.requireNonNull(title);
        this.title = title;
        return this;
    }
    
    @Override
     public DateStep content(String content) {
        Objects.requireNonNull(content);
        this.content = content;
        return this;
    }
    
    @Override
     public UserIdStep date(String date) {
        Objects.requireNonNull(date);
        this.date = date;
        return this;
    }
    
    @Override
     public BuildStep userId(String userId) {
        Objects.requireNonNull(userId);
        this.userId = userId;
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
    private CopyOfBuilder(String id, String title, String content, String date, String userId) {
      super.id(id);
      super.title(title)
        .content(content)
        .date(date)
        .userId(userId);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
    
    @Override
     public CopyOfBuilder content(String content) {
      return (CopyOfBuilder) super.content(content);
    }
    
    @Override
     public CopyOfBuilder date(String date) {
      return (CopyOfBuilder) super.date(date);
    }
    
    @Override
     public CopyOfBuilder userId(String userId) {
      return (CopyOfBuilder) super.userId(userId);
    }
  }
  
}
