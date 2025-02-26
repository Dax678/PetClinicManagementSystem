INSERT INTO public.User (username, password, email, role)
VALUES
    ('john1', '$2a$10$abcdefg1234567890hashedpassword1', 'john.doe@example.com','ROLE_OWNER'),
    ('jane2', '$2a$10$hijklmn1234567890hashedpassword2',  'jane.smith@example.com','ROLE_VETERINARIAN'),
    ('admin_user', '$2a$10$opqrst1234567890hashedpassword3', 'admin1@example.com','ROLE_ADMIN');

INSERT INTO public.Owner (id, first_name, last_name, phone, address, city, state, zip)
VALUES (1, 'John', 'Doe', '123456789', '123 Main St', 'Springfield', 'IL', '62704'),
       (2, 'Jane', 'Smith', '987654321', '456 Oak St', 'Shelbyville', 'IL', '62565');

INSERT INTO public.Patient (id, owner_id, name, date_of_birth, gender, weight, breed)
VALUES (1, 1, 'Buddy', '2018-05-10', 'MALE', 30.5, 'Golden Retriever'),
       (2, 2, 'Mittens', '2020-07-21', 'FEMALE', 4.2, 'Siamese Cat');

INSERT INTO public.Veterinarian (id, first_name, last_name, email, phone, work_start_date, specialization)
VALUES (1, 'Alice', 'Johnson', 'alice.johnson@example.com', '555123456', '2015-06-01', 'Surgery'),
       (2, 'Bob', 'Williams', 'bob.williams@example.com', '555987654', '2018-09-15', 'Dermatology');

INSERT INTO public.Appointment (id, patient_id, vet_id, appointment_date, description)
VALUES (1, 1, 1, '2024-02-15', 'Routine check-up'),
       (2, 2, 2, '2024-02-16', 'Skin allergy treatment');

INSERT INTO public.Medication (id, name, description, price)
VALUES (1, 'Antibiotics', 'Broad-spectrum antibiotics for infections', 20.00),
       (2, 'Antihistamine', 'For treating allergies', 15.00);

INSERT INTO public.Appointment_Medication (appointment_id, medication_id, quantity)
VALUES (1, 1, 1),
       (2, 2, 2);