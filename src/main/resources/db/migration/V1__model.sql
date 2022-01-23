-- public.brand definition

CREATE TABLE brand (
	id serial NOT NULL,
	name varchar(50) NOT NULL,
	createdat timestamp NOT NULL DEFAULT now(),
	updatedat timestamp NULL,

	CONSTRAINT brand_pk PRIMARY KEY (id),
	CONSTRAINT brand_name_key UNIQUE (name)
);

-- public.product definition

CREATE TABLE product (
	id bigserial NOT NULL,
	sku varchar(12) NOT NULL,
	name varchar(50) NOT NULL,
	size varchar(10) NOT NULL,
	price double precision NOT NULL,

	idbrand integer NOT NULL,

	createdat timestamp NOT NULL DEFAULT now(),
	updatedat timestamp NULL,

	CONSTRAINT product_pk PRIMARY KEY (id),
	CONSTRAINT product_sku_key UNIQUE (sku)
);

-- public.productimage definition

CREATE TABLE productimage (
	id bigserial NOT NULL,
	url varchar(100) NOT NULL,
	principal boolean NOT NULL,

	idproduct bigint NOT NULL,

	createdat timestamp NOT NULL DEFAULT now(),
	updatedat timestamp NULL,

	CONSTRAINT productimage_pk PRIMARY KEY (id),
	CONSTRAINT productimage_url_key UNIQUE (url)
);


-- foreign keys

ALTER TABLE public.product ADD CONSTRAINT brand_fk FOREIGN KEY (idbrand) REFERENCES brand(id) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE public.productimage ADD CONSTRAINT productimage_fk FOREIGN KEY (idproduct) REFERENCES product(id) ON UPDATE CASCADE ON DELETE CASCADE;

