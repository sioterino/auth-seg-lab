package ads.seg.authentication.models;

public interface UserRepository {

	void save(User user);

	void update(User user);

	User findByLogin(String login);
}
