# Generated by Django 3.1.5 on 2021-01-16 07:27

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('diaries', '0005_diary_is_visible'),
    ]

    operations = [
        migrations.AlterField(
            model_name='diary',
            name='emo_percent',
            field=models.IntegerField(default=50),
        ),
    ]
