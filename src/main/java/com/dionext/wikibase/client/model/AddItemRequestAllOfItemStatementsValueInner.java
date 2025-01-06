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
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * AddItemRequestAllOfItemStatementsValueInner
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-12-23T19:13:51.135774Z[Etc/UTC]", comments = "Generator version: 7.10.0")
public class AddItemRequestAllOfItemStatementsValueInner {
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
  private AddItemRequestAllOfItemStatementsValueInnerAllOfProperty property;

  public static final String SERIALIZED_NAME_VALUE = "value";
  @SerializedName(SERIALIZED_NAME_VALUE)
  @javax.annotation.Nullable
  private AddItemRequestAllOfItemStatementsValueInnerAllOfValue value;

  public static final String SERIALIZED_NAME_QUALIFIERS = "qualifiers";
  @SerializedName(SERIALIZED_NAME_QUALIFIERS)
  @javax.annotation.Nullable
  private List<AddItemRequestAllOfItemStatementsValueInnerAllOfQualifiersInner> qualifiers = new ArrayList<>();

  public static final String SERIALIZED_NAME_REFERENCES = "references";
  @SerializedName(SERIALIZED_NAME_REFERENCES)
  @javax.annotation.Nullable
  private List<AddItemRequestAllOfItemStatementsValueInnerAllOfReferencesInner> references = new ArrayList<>();

  public AddItemRequestAllOfItemStatementsValueInner() {
  }

  public AddItemRequestAllOfItemStatementsValueInner(
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



  public AddItemRequestAllOfItemStatementsValueInner rank(@javax.annotation.Nullable RankEnum rank) {
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


  public AddItemRequestAllOfItemStatementsValueInner property(@javax.annotation.Nullable AddItemRequestAllOfItemStatementsValueInnerAllOfProperty property) {
    this.property = property;
    return this;
  }

  /**
   * Get property
   * @return property
   */
  @javax.annotation.Nullable
  public AddItemRequestAllOfItemStatementsValueInnerAllOfProperty getProperty() {
    return property;
  }

  public void setProperty(@javax.annotation.Nullable AddItemRequestAllOfItemStatementsValueInnerAllOfProperty property) {
    this.property = property;
  }


  public AddItemRequestAllOfItemStatementsValueInner value(@javax.annotation.Nullable AddItemRequestAllOfItemStatementsValueInnerAllOfValue value) {
    this.value = value;
    return this;
  }

  /**
   * Get value
   * @return value
   */
  @javax.annotation.Nullable
  public AddItemRequestAllOfItemStatementsValueInnerAllOfValue getValue() {
    return value;
  }

  public void setValue(@javax.annotation.Nullable AddItemRequestAllOfItemStatementsValueInnerAllOfValue value) {
    this.value = value;
  }


  public AddItemRequestAllOfItemStatementsValueInner qualifiers(@javax.annotation.Nullable List<AddItemRequestAllOfItemStatementsValueInnerAllOfQualifiersInner> qualifiers) {
    this.qualifiers = qualifiers;
    return this;
  }

  public AddItemRequestAllOfItemStatementsValueInner addQualifiersItem(AddItemRequestAllOfItemStatementsValueInnerAllOfQualifiersInner qualifiersItem) {
    if (this.qualifiers == null) {
      this.qualifiers = new ArrayList<>();
    }
    this.qualifiers.add(qualifiersItem);
    return this;
  }

  /**
   * Get qualifiers
   * @return qualifiers
   */
  @javax.annotation.Nullable
  public List<AddItemRequestAllOfItemStatementsValueInnerAllOfQualifiersInner> getQualifiers() {
    return qualifiers;
  }

  public void setQualifiers(@javax.annotation.Nullable List<AddItemRequestAllOfItemStatementsValueInnerAllOfQualifiersInner> qualifiers) {
    this.qualifiers = qualifiers;
  }


  public AddItemRequestAllOfItemStatementsValueInner references(@javax.annotation.Nullable List<AddItemRequestAllOfItemStatementsValueInnerAllOfReferencesInner> references) {
    this.references = references;
    return this;
  }

  public AddItemRequestAllOfItemStatementsValueInner addReferencesItem(AddItemRequestAllOfItemStatementsValueInnerAllOfReferencesInner referencesItem) {
    if (this.references == null) {
      this.references = new ArrayList<>();
    }
    this.references.add(referencesItem);
    return this;
  }

  /**
   * Get references
   * @return references
   */
  @javax.annotation.Nullable
  public List<AddItemRequestAllOfItemStatementsValueInnerAllOfReferencesInner> getReferences() {
    return references;
  }

  public void setReferences(@javax.annotation.Nullable List<AddItemRequestAllOfItemStatementsValueInnerAllOfReferencesInner> references) {
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
    AddItemRequestAllOfItemStatementsValueInner addItemRequestAllOfItemStatementsValueInner = (AddItemRequestAllOfItemStatementsValueInner) o;
    return Objects.equals(this.id, addItemRequestAllOfItemStatementsValueInner.id) &&
        Objects.equals(this.rank, addItemRequestAllOfItemStatementsValueInner.rank) &&
        Objects.equals(this.property, addItemRequestAllOfItemStatementsValueInner.property) &&
        Objects.equals(this.value, addItemRequestAllOfItemStatementsValueInner.value) &&
        Objects.equals(this.qualifiers, addItemRequestAllOfItemStatementsValueInner.qualifiers) &&
        Objects.equals(this.references, addItemRequestAllOfItemStatementsValueInner.references);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, rank, property, value, qualifiers, references);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddItemRequestAllOfItemStatementsValueInner {\n");
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
  }

  /**
   * Validates the JSON Element and throws an exception if issues found
   *
   * @param jsonElement JSON Element
   * @throws IOException if the JSON Element is invalid with respect to AddItemRequestAllOfItemStatementsValueInner
   */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!AddItemRequestAllOfItemStatementsValueInner.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in AddItemRequestAllOfItemStatementsValueInner is not found in the empty JSON string", AddItemRequestAllOfItemStatementsValueInner.openapiRequiredFields.toString()));
        }
      }

      Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Map.Entry<String, JsonElement> entry : entries) {
        if (!AddItemRequestAllOfItemStatementsValueInner.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `AddItemRequestAllOfItemStatementsValueInner` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
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
      // validate the optional field `property`
      if (jsonObj.get("property") != null && !jsonObj.get("property").isJsonNull()) {
        AddItemRequestAllOfItemStatementsValueInnerAllOfProperty.validateJsonElement(jsonObj.get("property"));
      }
      // validate the optional field `value`
      if (jsonObj.get("value") != null && !jsonObj.get("value").isJsonNull()) {
        AddItemRequestAllOfItemStatementsValueInnerAllOfValue.validateJsonElement(jsonObj.get("value"));
      }
      if (jsonObj.get("qualifiers") != null && !jsonObj.get("qualifiers").isJsonNull()) {
        JsonArray jsonArrayqualifiers = jsonObj.getAsJsonArray("qualifiers");
        if (jsonArrayqualifiers != null) {
          // ensure the json data is an array
          if (!jsonObj.get("qualifiers").isJsonArray()) {
            throw new IllegalArgumentException(String.format("Expected the field `qualifiers` to be an array in the JSON string but got `%s`", jsonObj.get("qualifiers").toString()));
          }

          // validate the optional field `qualifiers` (array)
          for (int i = 0; i < jsonArrayqualifiers.size(); i++) {
            AddItemRequestAllOfItemStatementsValueInnerAllOfQualifiersInner.validateJsonElement(jsonArrayqualifiers.get(i));
          };
        }
      }
      if (jsonObj.get("references") != null && !jsonObj.get("references").isJsonNull()) {
        JsonArray jsonArrayreferences = jsonObj.getAsJsonArray("references");
        if (jsonArrayreferences != null) {
          // ensure the json data is an array
          if (!jsonObj.get("references").isJsonArray()) {
            throw new IllegalArgumentException(String.format("Expected the field `references` to be an array in the JSON string but got `%s`", jsonObj.get("references").toString()));
          }

          // validate the optional field `references` (array)
          for (int i = 0; i < jsonArrayreferences.size(); i++) {
            AddItemRequestAllOfItemStatementsValueInnerAllOfReferencesInner.validateJsonElement(jsonArrayreferences.get(i));
          };
        }
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!AddItemRequestAllOfItemStatementsValueInner.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'AddItemRequestAllOfItemStatementsValueInner' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<AddItemRequestAllOfItemStatementsValueInner> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(AddItemRequestAllOfItemStatementsValueInner.class));

       return (TypeAdapter<T>) new TypeAdapter<AddItemRequestAllOfItemStatementsValueInner>() {
           @Override
           public void write(JsonWriter out, AddItemRequestAllOfItemStatementsValueInner value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public AddItemRequestAllOfItemStatementsValueInner read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

  /**
   * Create an instance of AddItemRequestAllOfItemStatementsValueInner given an JSON string
   *
   * @param jsonString JSON string
   * @return An instance of AddItemRequestAllOfItemStatementsValueInner
   * @throws IOException if the JSON string is invalid with respect to AddItemRequestAllOfItemStatementsValueInner
   */
  public static AddItemRequestAllOfItemStatementsValueInner fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, AddItemRequestAllOfItemStatementsValueInner.class);
  }

  /**
   * Convert an instance of AddItemRequestAllOfItemStatementsValueInner to an JSON string
   *
   * @return JSON string
   */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}
