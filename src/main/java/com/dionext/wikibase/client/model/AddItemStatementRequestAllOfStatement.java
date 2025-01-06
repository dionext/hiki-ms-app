/*
 * Wikibase REST API
 * OpenAPI definition of Wikibase REST API
 *
 * The version of the OpenAPI document: 1.1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package com.dionext.wikibase.client.model;

import java.util.Objects;

import com.dionext.wikibase.client.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Arrays;
import org.openapitools.jackson.nullable.JsonNullable;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * AddItemStatementRequestAllOfStatement
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-12-23T19:13:51.135774Z[Etc/UTC]", comments = "Generator version: 7.10.0")
public class AddItemStatementRequestAllOfStatement {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  @javax.annotation.Nullable
  private String id;

  /**
   * The rank of the Statement
   */
  @JsonAdapter(RankEnum.Adapter.class)
  public enum RankEnum {
    @JsonProperty("deprecated")
    DEPRECATED("deprecated"),

    @JsonProperty("normal")
    NORMAL("normal"),

    @JsonProperty("preferred")
    PREFERRED("preferred");

    private String value;

    RankEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static RankEnum fromValue(String value) {
      for (RankEnum b : RankEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<RankEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final RankEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public RankEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return RankEnum.fromValue(value);
      }
    }

    public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      String value = jsonElement.getAsString();
      RankEnum.fromValue(value);
    }
  }

  public static final String SERIALIZED_NAME_RANK = "rank";
  @SerializedName(SERIALIZED_NAME_RANK)
  @javax.annotation.Nullable
  private RankEnum rank = RankEnum.NORMAL;

  public static final String SERIALIZED_NAME_PROPERTY = "property";
  @SerializedName(SERIALIZED_NAME_PROPERTY)
  @javax.annotation.Nullable
  private Object property = null;

  public static final String SERIALIZED_NAME_VALUE = "value";
  @SerializedName(SERIALIZED_NAME_VALUE)
  @javax.annotation.Nullable
  private Object value = null;

  public static final String SERIALIZED_NAME_QUALIFIERS = "qualifiers";
  @SerializedName(SERIALIZED_NAME_QUALIFIERS)
  @javax.annotation.Nullable
  private Object qualifiers = null;

  public static final String SERIALIZED_NAME_REFERENCES = "references";
  @SerializedName(SERIALIZED_NAME_REFERENCES)
  @javax.annotation.Nullable
  private Object references = null;

  public AddItemStatementRequestAllOfStatement() {
  }

  public AddItemStatementRequestAllOfStatement(
     String id
  ) {
    this();
    this.id = id;
  }

  /**
   * The globally unique identifier for this Statement
   * @return id
   */
  @javax.annotation.Nullable
  public String getId() {
    return id;
  }



  public AddItemStatementRequestAllOfStatement rank(@javax.annotation.Nullable RankEnum rank) {
    this.rank = rank;
    return this;
  }

  /**
   * The rank of the Statement
   * @return rank
   */
  @javax.annotation.Nullable
  public RankEnum getRank() {
    return rank;
  }

  public void setRank(@javax.annotation.Nullable RankEnum rank) {
    this.rank = rank;
  }


  public AddItemStatementRequestAllOfStatement property(@javax.annotation.Nullable Object property) {
    this.property = property;
    return this;
  }

  /**
   * Get property
   * @return property
   */
  @javax.annotation.Nullable
  public Object getProperty() {
    return property;
  }

  public void setProperty(@javax.annotation.Nullable Object property) {
    this.property = property;
  }


  public AddItemStatementRequestAllOfStatement value(@javax.annotation.Nullable Object value) {
    this.value = value;
    return this;
  }

  /**
   * Get value
   * @return value
   */
  @javax.annotation.Nullable
  public Object getValue() {
    return value;
  }

  public void setValue(@javax.annotation.Nullable Object value) {
    this.value = value;
  }


  public AddItemStatementRequestAllOfStatement qualifiers(@javax.annotation.Nullable Object qualifiers) {
    this.qualifiers = qualifiers;
    return this;
  }

  /**
   * Get qualifiers
   * @return qualifiers
   */
  @javax.annotation.Nullable
  public Object getQualifiers() {
    return qualifiers;
  }

  public void setQualifiers(@javax.annotation.Nullable Object qualifiers) {
    this.qualifiers = qualifiers;
  }


  public AddItemStatementRequestAllOfStatement references(@javax.annotation.Nullable Object references) {
    this.references = references;
    return this;
  }

  /**
   * Get references
   * @return references
   */
  @javax.annotation.Nullable
  public Object getReferences() {
    return references;
  }

  public void setReferences(@javax.annotation.Nullable Object references) {
    this.references = references;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddItemStatementRequestAllOfStatement addItemStatementRequestAllOfStatement = (AddItemStatementRequestAllOfStatement) o;
    return Objects.equals(this.id, addItemStatementRequestAllOfStatement.id) &&
        Objects.equals(this.rank, addItemStatementRequestAllOfStatement.rank) &&
        Objects.equals(this.property, addItemStatementRequestAllOfStatement.property) &&
        Objects.equals(this.value, addItemStatementRequestAllOfStatement.value) &&
        Objects.equals(this.qualifiers, addItemStatementRequestAllOfStatement.qualifiers) &&
        Objects.equals(this.references, addItemStatementRequestAllOfStatement.references);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, rank, property, value, qualifiers, references);
  }

  private static <T> int hashCodeNullable(JsonNullable<T> a) {
    if (a == null) {
      return 1;
    }
    return a.isPresent() ? Arrays.deepHashCode(new Object[]{a.get()}) : 31;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddItemStatementRequestAllOfStatement {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    rank: ").append(toIndentedString(rank)).append("\n");
    sb.append("    property: ").append(toIndentedString(property)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    qualifiers: ").append(toIndentedString(qualifiers)).append("\n");
    sb.append("    references: ").append(toIndentedString(references)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


  public static HashSet<String> openapiFields;
  public static HashSet<String> openapiRequiredFields;

  static {
    // a set of all properties/fields (JSON key names)
    openapiFields = new HashSet<String>();
    openapiFields.add("id");
    openapiFields.add("rank");
    openapiFields.add("property");
    openapiFields.add("value");
    openapiFields.add("qualifiers");
    openapiFields.add("references");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("property");
    openapiRequiredFields.add("value");
  }

  /**
   * Validates the JSON Element and throws an exception if issues found
   *
   * @param jsonElement JSON Element
   * @throws IOException if the JSON Element is invalid with respect to AddItemStatementRequestAllOfStatement
   */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!AddItemStatementRequestAllOfStatement.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in AddItemStatementRequestAllOfStatement is not found in the empty JSON string", AddItemStatementRequestAllOfStatement.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!AddItemStatementRequestAllOfStatement.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `AddItemStatementRequestAllOfStatement` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : AddItemStatementRequestAllOfStatement.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("id") != null && !jsonObj.get("id").isJsonNull()) && !jsonObj.get("id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("id").toString()));
      }
      if ((jsonObj.get("rank") != null && !jsonObj.get("rank").isJsonNull()) && !jsonObj.get("rank").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `rank` to be a primitive type in the JSON string but got `%s`", jsonObj.get("rank").toString()));
      }
      // validate the optional field `rank`
      if (jsonObj.get("rank") != null && !jsonObj.get("rank").isJsonNull()) {
        RankEnum.validateJsonElement(jsonObj.get("rank"));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!AddItemStatementRequestAllOfStatement.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'AddItemStatementRequestAllOfStatement' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<AddItemStatementRequestAllOfStatement> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(AddItemStatementRequestAllOfStatement.class));

       return (TypeAdapter<T>) new TypeAdapter<AddItemStatementRequestAllOfStatement>() {
           @Override
           public void write(JsonWriter out, AddItemStatementRequestAllOfStatement value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public AddItemStatementRequestAllOfStatement read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

  /**
   * Create an instance of AddItemStatementRequestAllOfStatement given an JSON string
   *
   * @param jsonString JSON string
   * @return An instance of AddItemStatementRequestAllOfStatement
   * @throws IOException if the JSON string is invalid with respect to AddItemStatementRequestAllOfStatement
   */
  public static AddItemStatementRequestAllOfStatement fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, AddItemStatementRequestAllOfStatement.class);
  }

  /**
   * Convert an instance of AddItemStatementRequestAllOfStatement to an JSON string
   *
   * @return JSON string
   */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

