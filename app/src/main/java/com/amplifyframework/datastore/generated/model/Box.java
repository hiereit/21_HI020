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

/** This is an auto generated class representing the Box type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Boxes")
public final class Box implements Model {
  public static final QueryField ID = field("Box", "id");
  public static final QueryField ADDRESS = field("Box", "address");
  public static final QueryField CENTER_ID = field("Box", "centerId");
  public static final QueryField WEIGHT = field("Box", "weight");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String address;
  private final @ModelField(targetType="String", isRequired = true) String centerId;
  private final @ModelField(targetType="Float", isRequired = true) Double weight;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getAddress() {
      return address;
  }
  
  public String getCenterId() {
      return centerId;
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
  
  private Box(String id, String address, String centerId, Double weight) {
    this.id = id;
    this.address = address;
    this.centerId = centerId;
    this.weight = weight;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Box box = (Box) obj;
      return ObjectsCompat.equals(getId(), box.getId()) &&
              ObjectsCompat.equals(getAddress(), box.getAddress()) &&
              ObjectsCompat.equals(getCenterId(), box.getCenterId()) &&
              ObjectsCompat.equals(getWeight(), box.getWeight()) &&
              ObjectsCompat.equals(getCreatedAt(), box.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), box.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getAddress())
      .append(getCenterId())
      .append(getWeight())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Box {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("address=" + String.valueOf(getAddress()) + ", ")
      .append("centerId=" + String.valueOf(getCenterId()) + ", ")
      .append("weight=" + String.valueOf(getWeight()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static AddressStep builder() {
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
  public static Box justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Box(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      address,
      centerId,
      weight);
  }
  public interface AddressStep {
    CenterIdStep address(String address);
  }
  

  public interface CenterIdStep {
    WeightStep centerId(String centerId);
  }
  

  public interface WeightStep {
    BuildStep weight(Double weight);
  }
  

  public interface BuildStep {
    Box build();
    BuildStep id(String id) throws IllegalArgumentException;
  }
  

  public static class Builder implements AddressStep, CenterIdStep, WeightStep, BuildStep {
    private String id;
    private String address;
    private String centerId;
    private Double weight;
    @Override
     public Box build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Box(
          id,
          address,
          centerId,
          weight);
    }
    
    @Override
     public CenterIdStep address(String address) {
        Objects.requireNonNull(address);
        this.address = address;
        return this;
    }
    
    @Override
     public WeightStep centerId(String centerId) {
        Objects.requireNonNull(centerId);
        this.centerId = centerId;
        return this;
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
    private CopyOfBuilder(String id, String address, String centerId, Double weight) {
      super.id(id);
      super.address(address)
        .centerId(centerId)
        .weight(weight);
    }
    
    @Override
     public CopyOfBuilder address(String address) {
      return (CopyOfBuilder) super.address(address);
    }
    
    @Override
     public CopyOfBuilder centerId(String centerId) {
      return (CopyOfBuilder) super.centerId(centerId);
    }
    
    @Override
     public CopyOfBuilder weight(Double weight) {
      return (CopyOfBuilder) super.weight(weight);
    }
  }
  
}
