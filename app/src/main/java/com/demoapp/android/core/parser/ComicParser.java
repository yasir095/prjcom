package com.demoapp.android.core.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.demoapp.android.core.extras.Logger;
import com.demoapp.android.comics.model.Comic;
import com.demoapp.android.comics.model.Author;
import com.demoapp.android.comics.model.Price;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yasirmahmood.
 */

public class ComicParser extends Parser<ArrayList<Comic>>
{
    @Override
    public ArrayList<Comic> getParsedData(String jsonObject)
    {
        ArrayList<Comic> comics = new ArrayList<>();
        try
        {
            TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {};
            HashMap<String, Object> hashMap = mapper.readValue(jsonObject, typeRef);

            ArrayList<HashMap<String, Object>> results = (ArrayList<HashMap<String, Object>>) ((HashMap<String, Object>) hashMap.get("data")).get("results");

            for (HashMap<String, ?> map: results)
            {
                Comic comic = new Comic();
                comic.setId(map.get("id")+"");
                comic.setTitle((String) map.get("title"));
                comic.setDescription(map.get("description")+"");


                if(map.get("prices") != null)
                {
                    //comic.setPrices((ArrayList<Price>) map.get("prices"));

                    for (HashMap<String, Object> priceMap : (ArrayList<HashMap<String, Object>>) map.get("prices"))
                    {
                        Price price = new Price();
                        price.setPrice(String.valueOf(priceMap.get("price")));
                        price.setType((String) priceMap.get("type"));

                        comic.addPrice(price);
                    }
                }

                comic.setThumbnail((HashMap<String, String>) map.get("thumbnail"));

                HashMap<String, ?> creators = (HashMap<String, ?>) map.get("creators");

                if((Integer) creators.get("available")>0)
                {
                    //comic.setAuthors((ArrayList<Author>) creators.get("items"));

                    for (HashMap<String, String> authorMap : (ArrayList<HashMap<String, String>>) creators.get("items"))
                    {
                        Author author = new Author();
                        author.setName(String.valueOf(authorMap.get("name")));
                        author.setRole(String.valueOf(authorMap.get("role")));
                        author.setUrl(String.valueOf(authorMap.get("resourceURI")));

                        comic.addAuthors(author);
                    }
                }

                comics.add(comic);
            }

        }
        catch (Exception e)
        {
            Logger.logError(e.getMessage()+" |");
        }

        return comics;
    }
}
