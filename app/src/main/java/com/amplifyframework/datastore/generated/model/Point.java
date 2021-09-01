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

/** This is an auto generated class representing the Point type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Points")
public final class Point implements Model {
  public static final QueryField ID = field("Point", "id");
  public static final QueryField USER_ID = field("Point", "userId");
  public static final QueryField DATE = field("Point", "date");
  public static final QueryField VALUE = field("Point", "value");
  public static final QueryField WEIGHT = field("Point", "weight");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String userId;
  private final @ModelField(targetType="String", isRequired = true) String date;
  private final @ModelField(targetType="Int", isRequired = true) Integer value;
  private final @ModelField(targetType="Float") Double weight;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getUserId() {
      return userId;
  }
  
  public String getDate() {
      return date;
  }
  
  public Integer getValue() {
      return value;
  }
  
  public Double getWeight() {
      return weight;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Point(String id, String userId, String date, Integer value, Double weight) {
    this.id = id;
    this.userId = userId;
    this.date = date;
    this.value = value;
    this.weight = weight;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Point point = (Point) obj;
      return ObjectsCompat.equals(getId(), point.getId()) &&
              ObjectsCompat.equals(getUserId(), point.getUserId()) &&
              ObjectsCompat.equals(getDate(), point.getDate()) &&
              ObjectsCompat.equals(getValue(), point.getValue()) &&
              ObjectsCompat.equals(getWeight(), point.getWeight()) &&
              ObjectsCompat.equals(getCreatedAt(), point.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), point.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUserId())
      .append(getDate())
      .append(getValue())
      .append(getWeight())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Point {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("userId=" + String.valueOf(getUserId()) + ", ")
      .append("date=" + String.valueOf(getDate()) + ", ")
      .append("value=" + String.valueOf(getValue()) + ", ")
      .append("weight=" + String.valueOf(getWeight()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static UserIdStep builder() {
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
  public static Point justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Point(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      userId,
      date,
      value,
      weight);
  }
  public interface UserIdStep {
    DateStep userId(String userId);
  }
  

  public interface DateStep {
    ValueStep date(String date);
  }
  

  public interface ValueStep {
    BuildStep value(Integer value);
  }
  

  public interface BuildStep {
    Point build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep weight(Double weight);
  }
  

  public static class Builder implements UserIdStep, DateStep, ValueStep, BuildStep {
    private String id;
    private String userId;
    private String date;
    private Integer value;
    private Double weight;
    @Override
     public Point build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Point(
          id,
          userId,
          date,
          value,
          weight);
    }
    
    @Override
     public DateStep userId(String userId) {
        Objects.requireNonNull(userId);
        this.userId = userId;
        return this;
    }
    
    @Override
     public ValueStep date(String date) {
        Objects.requireNonNull(date);
        this.date = date;
        return this;
    }
    
    @Override
     public BuildStep value(Integer value) {
        Objects.requireNonNull(value);
        this.value = value;
        return this;
    }
    
    @Override
     public BuildStep weight(Double weight) {
        this.weight = weight;
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
    private CopyOfBuilder(String id, String userId, String date, Integer value, Double weight) {
      super.id(id);
      super.userId(userId)
        .date(date)
        .value(value)
        .weight(weight);
    }
    
    @Override
     public CopyOfBuilder userId(String userId) {
      return (CopyOfBuilder) super.userId(userId);
    }
    
    @Override
     public CopyOfBuilder date(String date) {
      return (CopyOfBuilder) super.date(date);
    }
    
    @Override
     public CopyOfBuilder value(Integer value) {
      return (CopyOfBuilder) super.value(value);
    }
    
    @Override
     public CopyOfBuilder weight(Double weight) {
      return (CopyOfBuilder) super.weight(weight);
    }
  }
  
}
