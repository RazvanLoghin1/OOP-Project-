package server.api;

import commons.Match;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.MatchRepository;

public class TestMatchRepository implements MatchRepository {
    public final List<Match> matches = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();

    private void call(String name) {
        calledMethods.add(name);
    }

    @Override
    public List<Match> findAll() {
        calledMethods.add("findAll");
        return matches;
    }

    @Override
    public List<Match> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Match> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Match> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return matches.size();
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Match entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Match> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Match> S save(S entity) {
        call("save");
        entity.id = (long) matches.size();
        matches.add(entity);
        return entity;
    }

    @Override
    public <S extends Match> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Match> findById(Long aLong) {
        call("findById");
        return Optional.of(find(aLong).get());
    }

    @Override
    public boolean existsById(Long aLong) {
        call("existsById");
        return find(aLong).isPresent();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Match> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Match> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Match> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Match getOne(Long aLong) {
        return null;
    }

    @Override
    public Match getById(Long aLong) {
        call("getById");
        return find(aLong).get();
    }

    private Optional<Match> find(Long id) {
        return matches.stream().filter(q -> q.id == id).findFirst();
    }

    @Override
    public <S extends Match> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Match> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Match> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Match> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Match> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Match> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Match, R> R findBy(Example<S> example,
                                         Function<FluentQuery.FetchableFluentQuery<S>,
                                                 R> queryFunction) {
        return null;
    }
}
