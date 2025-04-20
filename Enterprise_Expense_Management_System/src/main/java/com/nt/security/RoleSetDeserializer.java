package com.nt.security;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.nt.model.Role;
public class RoleSetDeserializer extends JsonDeserializer<Role> {
    @Override
    public Role deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonToken currentToken = p.currentToken();
        Role role = new Role();

        if (currentToken == JsonToken.VALUE_STRING) {
            String roleName = p.getText();
            role.setName(roleName);
        } else if (currentToken == JsonToken.START_ARRAY) {
            // Only handle the first item in the array for single role case
            p.nextToken();  // Move to the first element
            String roleName = p.getValueAsString();
            role.setName(roleName);
            p.nextToken();  // Move past the first element
        } else {
            throw JsonMappingException.from(p, "Expected string or array for role");
        }

        return role;
    }
}
