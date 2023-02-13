package com.garamgaebi.GaramgaebiServer.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QIceBreakingImages is a Querydsl query type for IceBreakingImages
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIceBreakingImages extends EntityPathBase<IceBreakingImages> {

    private static final long serialVersionUID = 782631131L;

    public static final QIceBreakingImages iceBreakingImages = new QIceBreakingImages("iceBreakingImages");

    public final NumberPath<Long> iceBreakingImagesIdx = createNumber("iceBreakingImagesIdx", Long.class);

    public final StringPath url = createString("url");

    public QIceBreakingImages(String variable) {
        super(IceBreakingImages.class, forVariable(variable));
    }

    public QIceBreakingImages(Path<? extends IceBreakingImages> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIceBreakingImages(PathMetadata metadata) {
        super(IceBreakingImages.class, metadata);
    }

}

