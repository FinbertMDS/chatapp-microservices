package com.finbertmds.microservice.message.security.repository;

import com.finbertmds.microservice.message.entity.PersistentTokenEntity;

import org.springframework.stereotype.Repository;


@Repository
public class PersistentTokenRepository {



    // TODO: update
    // private final PersistentTokenEntity_Manager manager;

    // @Inject
    // public PersistentTokenRepository(PersistentTokenEntity_Manager manager) {
    //     this.manager = manager;
    // }

    public void insert(PersistentTokenEntity token) {
        // final PersistentTokenEntity entity = new PersistentTokenEntity(token.getSeries(), token.getTokenValue(),
        //         token.getTokenDate(), token.getIpAddress(),
        //         token.getUserAgent(), token.getLogin(),
        //         token.getPass(), token.getAuthorities());

        // manager
        //         .crud()
        //         .insert(entity)
        //         .execute();
    }

    public void deleteById(String series) {
        // manager.crud().deleteById(series).execute();
    }

    public PersistentTokenEntity findById(String presentedSeries) {
        return new PersistentTokenEntity();
        // return manager.crud().findById(presentedSeries).get();
    }

    public void update(PersistentTokenEntity token) {
        // manager
        //         .dsl()
        //         .update()
        //         .fromBaseTable()
        //         .tokenValue().Set(token.getTokenValue())
        //         .where()
        //         .series().Eq(token.getSeries())
        //         .usingTimeToLive(PersistentTokenEntity.TOKEN_VALIDITY_SECONDS)
        //         .execute();
    }
}
