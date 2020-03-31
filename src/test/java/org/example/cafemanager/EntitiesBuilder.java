package org.example.cafemanager;

import org.example.cafemanager.domain.*;
import org.example.cafemanager.domain.enums.Role;
import org.example.cafemanager.domain.enums.Status;
import org.example.cafemanager.dto.table.SimpleTableProps;
import org.example.cafemanager.dto.user.CreateUserRequest;
import org.example.cafemanager.dto.user.UpdateUserRequestBody;
import org.example.cafemanager.dto.user.UserPublicProfile;

public class EntitiesBuilder {
    public final static String email = "test@test.test";

    public static User createUser() {
        User u = new User();
        u.setEmail(email);
        u.setUsername(Util.randomString(6));
        u.setPassword(Util.randomString(6));
        u.setFirstName(Util.randomString(6));
        u.setLastName(Util.randomString(6));
        u.setPassword(Util.randomString(6));
        u.setRole(Role.WAITER);
        return u;
    }

    public static User createUser(String username) {
        User u = createUser();
        u.setUsername(username);
        return u;
    }

    public static CafeTable createCafeTable(String tableName) {
        CafeTable cafeTable = new CafeTable();
        cafeTable.setName(tableName);
        return cafeTable;
    }

    public static CafeTable createCafeTable() {
        CafeTable cafeTable = new CafeTable();
        cafeTable.setName(Util.randomString(6));
        return cafeTable;
    }

    public static CafeTable createCafeTable(User user) {
        CafeTable cafeTable = new CafeTable();
        cafeTable.setName(Util.randomString(6));
        cafeTable.setUser(user);
        return cafeTable;
    }

    public static Order createOrder() {
        Order o = new Order();
        o.setCafeTable(createCafeTable());
        o.setStatus(Status.OPEN);
        return o;
    }

    public static Product createProduct(String productName) {
        Product product = new Product();
        product.setName(productName);
        return product;
    }

    public static Product createProduct() {
        Product product = new Product();
        product.setName(Util.randomString(6));
        return product;
    }

    public static ProductsInOrder createProductInOrder() {
        ProductsInOrder p = new ProductsInOrder();
        Order o = createOrder();
        o.setCafeTable(createCafeTable());
        p.setOrder(o);
        p.setProduct(createProduct());
        p.setAmount(1);

        return p;
    }

    public static CreateUserRequest createCreateUserRequest() {
        return new CreateUserRequest(
                Util.randomString(6),
                Util.randomString(6),
                email
        );
    }

    public static UpdateUserRequestBody createUpdateUserRequestBody() {
        UpdateUserRequestBody u = new UpdateUserRequestBody();
        u.setFirstName(Util.randomString(6));
        u.setLastName(Util.randomString(6));
        return u;
    }

    public static UserPublicProfile createUserPublicProfile(Long userId) {
        return new UserPublicProfile() {
            @Override
            public Long getId() {
                return userId;
            }

            @Override
            public String getFirstName() {
                return null;
            }

            @Override
            public String getLastName() {
                return null;
            }

            @Override
            public String getUsername() {
                return Util.randomString(6);
            }

            @Override
            public String getEmail() {
                return email;
            }
        };
    }

    public static SimpleTableProps createSimpleTableProps(Long tableId) {
        return new SimpleTableProps() {
            @Override
            public Long getId() {
                return tableId;
            }

            @Override
            public String getName() {
                return Util.randomString(4);
            }

            @Override
            public FetchedUser getUser() {
                User u = EntitiesBuilder.createUser();
                return new FetchedUser() {
                    @Override
                    public Long getId() {
                        return u.getId();
                    }

                    @Override
                    public String getUsername() {
                        return u.getUsername();
                    }
                };
            }
        };
    }
}
