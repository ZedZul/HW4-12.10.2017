package com.zedzul.github.hw.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "userApi",
        version = "v1",
        resource = "user",
        namespace = @ApiNamespace(
                ownerDomain = "backend.hw.github.zedzul.com",
                ownerName = "backend.hw.github.zedzul.com",
                packagePath = ""
        )
)
public class UserEndpoint {

    private static final Logger logger = Logger.getLogger(UserEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    private long usersCount = 1;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(User.class);
    }

    /**
     * Returns the {@link User} with the corresponding ID.
     *
     * @param mId the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code User} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "user/{mId}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public User get(@Named("mId") final long mId) throws NotFoundException {
        logger.info("Getting User with ID: " + mId);
        final User user = ofy().load().type(User.class).id(mId).now();
        if (user == null) {
            throw new NotFoundException("Could not find User with ID: " + mId);
        }
        return user;
    }

    /**
     * Inserts a new {@code User}.
     */
    @ApiMethod(
            name = "insert",
            httpMethod = ApiMethod.HttpMethod.POST)
    public void insert(@Named("mName") final String name, @Named("mAvatarUrl") final String avatarUrl, @Named("mDateOfBirth") final String dob) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that user.mId has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.

        User user = null;
        try {
            user = new User(usersCount, name, avatarUrl, new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH).parse(dob).getTime());
        } catch (final ParseException pE) {
            logger.info(pE.getMessage());
        }
        if (user != null) {
            usersCount++;
            ofy().save().entity(user).now();
            logger.info("Created User.");
        }

        //return ofy().load().entity(user).now();

    }

    /**
     * Return number of  registered users.
     */
    @ApiMethod(
            name = "number",
            path = "user/number",
            httpMethod = ApiMethod.HttpMethod.GET)
    public UsersCounter number() {

        return new UsersCounter(usersCount);

    }

    /**
     * Updates an existing {@code User}.
     *
     * @param mId  the ID of the entity to be updated
     * @param user the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code mId} does not correspond to an existing
     *                           {@code User}
     */
    @ApiMethod(
            name = "update",
            path = "user/{mId}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public User update(@Named("mId") final long mId, final User user) throws NotFoundException {

        checkExists(mId);
        ofy().save().entity(user).now();
        logger.info("Updated User: " + user);
        return ofy().load().entity(user).now();
    }

    /**
     * Deletes the specified {@code User}.
     *
     * @param mId the ID of the entity to delete
     * @throws NotFoundException if the {@code mId} does not correspond to an existing
     *                           {@code User}
     */
    @ApiMethod(
            name = "remove",
            path = "user/{mId}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("mId") final long mId) throws NotFoundException {
        checkExists(mId);
        ofy().delete().type(User.class).id(mId).now();
        logger.info("Deleted User with ID: " + mId);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "user/list",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<User> list(@Nullable @Named("cursor") final String cursor, @Nullable @Named("limit") Integer limit) {
        if(limit == null) {
            limit = DEFAULT_LIST_LIMIT;
        }
        Query<User> query = ofy().load().type(User.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        final QueryResultIterator<User> queryIterator = query.iterator();
        final List<User> userList = new ArrayList<>(limit);
        while (queryIterator.hasNext()) {
            userList.add(queryIterator.next());
        }
        return CollectionResponse.<User>builder().setItems(userList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(final long mId) throws NotFoundException {
        try {
            ofy().load().type(User.class).id(mId).safe();
        } catch (final com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find User with ID: " + mId);
        }
    }
}