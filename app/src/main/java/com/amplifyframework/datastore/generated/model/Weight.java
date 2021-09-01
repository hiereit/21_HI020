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

/** This is an auto generated class representing the Weight type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Weights")
public final class Weight implements Model {
  public static final QueryField ID = field("Weight", "id");
  public static final QueryField WEIGHT = field("Weight", "weight");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Float", isRequired = true) Double weight;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
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
  
  private Weight(String id, Double weight) {
    this.id = id;
    this.weight = weight;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Weight weight = (Weight) obj;
      return ObjectsCompat.equals(getId(), weight.getId()) &&
              ObjectsCompat.equals(getWeight(), weight.getWeight()) &&
              ObjectsCompat.equals(getCreatedAt(), weight.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), weight.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getWeight())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Weight {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("weight=" + String.valueOf(getWeight()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static WeightStep builder() {
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
  public static Weight justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Weight(
      id,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      weight);
  }
  public interface WeightStep {
    BuildStep weight(Double weight);
  }
  

  public interface BuildStep {
    Weight build();
    BuildStep id(String id) throws IllegalArgumentException;
  }
  

  public static class Builder implements WeightStep, BuildStep {
    private String id;
    private Double weight;
    @Override
     public Weight build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Weight(
          id,
          weight);
    }
    
    @Override
     public BuildStep weight(Double weight) {
        Objects.requireNonNull(weight);
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
    private CopyOfBuilder(String id, Double weight) {
      super.id(id);
      super.weight(weight);
    }
    
    @Override
     public CopyOfBuilder weight(Double weight) {
      return (CopyOfBuilder) super.weight(weight);
    }
  }
  
}
