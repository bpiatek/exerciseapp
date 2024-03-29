package pl.bpiatek.exerciseapp.github.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import pl.bpiatek.exerciseapp.github.api.app.GithubEntityView;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

/**
 * Created by Bartosz Piatek on 25/06/2022
 */
@Entity
@Getter
@NoArgsConstructor
class GithubEntity {

  @Id
  private String login;
  private int requestCount = 1;

  @Version
  private long version;

  public GithubEntity(String login) {
    this.login = login;
  }

  void increaseRequestCount() {
    this.requestCount++;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    GithubEntity that = (GithubEntity) o;
    return login != null && Objects.equals(login, that.login);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  GithubEntityView mapToDto() {
    return new GithubEntityView(
        this.login,
        this.requestCount,
        this.version
    );
  }
}
