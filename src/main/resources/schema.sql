CREATE TABLE public.User
(
    id       SERIAL             PRIMARY KEY,
    username VARCHAR(255)       UNIQUE NOT NULL,
    password VARCHAR(255)       NOT NULL,
    email    VARCHAR(100)       NOT NULL,
    role     VARCHAR(50)        NOT NULL
);


CREATE TABLE IF NOT EXISTS public.Owner
(
    id         SERIAL PRIMARY KEY,
    user_id    INT,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    phone      VARCHAR(15)  NOT NULL,
    address    VARCHAR(50)  NOT NULL,
    city       VARCHAR(50)  NOT NULL,
    state      VARCHAR(50)  NOT NULL,
    zip        VARCHAR(50)  NOT NULL,
    FOREIGN KEY (user_id) REFERENCES public.User(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.Patient
(
    id            SERIAL PRIMARY KEY,
    owner_id      INT,
    name          VARCHAR(50)      NOT NULL,
    date_of_birth DATE             NOT NULL,
    gender        VARCHAR(6)       NOT NULL,
    weight        DOUBLE PRECISION NOT NULL,
    breed         VARCHAR(50)      NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES public.Owner (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.Veterinarian
(
    id              SERIAL PRIMARY KEY,
    user_id         INT,
    first_name      VARCHAR(50)  NOT NULL,
    last_name       VARCHAR(50)  NOT NULL,
    email           VARCHAR(100) NOT NULL,
    phone           VARCHAR(15)  NOT NULL,
    work_start_date DATE         NOT NULL,
    specialization  VARCHAR(50)  NOT NULL,
    FOREIGN KEY (user_id) REFERENCES public.User(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.Appointment
(
    id               SERIAL PRIMARY KEY,
    patient_id       INT,
    vet_id           INT,
    appointment_date DATE NOT NULL,
    description      VARCHAR(100),
    FOREIGN KEY (patient_id) REFERENCES public.Patient (id) ON DELETE CASCADE,
    FOREIGN KEY (vet_id) REFERENCES public.Veterinarian (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS public.Medication
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(100)   NOT NULL,
    description TEXT,
    price       DECIMAL(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS public.Appointment_Medication
(
    appointment_id INT,
    medication_id  INT,
    quantity       INT NOT NULL,
    PRIMARY KEY (appointment_id, medication_id),
    FOREIGN KEY (appointment_id) REFERENCES public.Appointment (id) ON DELETE CASCADE,
    FOREIGN KEY (medication_id) REFERENCES public.Medication (id) ON DELETE CASCADE
);